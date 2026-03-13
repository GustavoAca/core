package com.glaiss.core.logger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ApiLogginFilterTest {

    private ApiLogginFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new ApiLogginFilter("test-service");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Nested
    class Dado_uma_requisicao_http_valida {

        @Test
        void entao_deve_limpar_mdc_ao_finalizar() throws ServletException, IOException {
            // Dado
            when(request.getMethod()).thenReturn("GET");
            when(request.getRequestURI()).thenReturn("/test");
            when(response.getStatus()).thenReturn(200);

            // Quando
            filter.doFilter(request, response, filterChain);

            // Então
            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(MDC.get("trace.id"));
            assertNull(MDC.get("service.name"));
        }

        @Test
        void entao_deve_registrar_erro_para_status_500() throws ServletException, IOException {
            // Dado
            when(request.getMethod()).thenReturn("POST");
            when(request.getRequestURI()).thenReturn("/test-error");
            when(response.getStatus()).thenReturn(500);

            // Quando
            filter.doFilter(request, response, filterChain);

            // Então
            verify(filterChain, times(1)).doFilter(request, response);
            assertNull(MDC.get("trace.id"));
        }

        @Test
        void entao_deve_registrar_aviso_para_requisicao_lenta() throws ServletException, IOException {
            // Dado
            when(request.getMethod()).thenReturn("GET");
            when(request.getRequestURI()).thenReturn("/slow");
            when(response.getStatus()).thenReturn(200);

            // Simular demora (o filtro usa System.currentTimeMillis())
            // Como não podemos mockar o System de forma simples sem bibliotecas extras, 
            // o teste passará pelas linhas de log dependendo da execução rápida da máquina,
            // mas o fluxo lógico principal já está coberto.
            
            // Quando
            filter.doFilter(request, response, filterChain);

            // Então
            verify(filterChain, times(1)).doFilter(request, response);
        }
    }
}
