package com.glaiss.core.controller;

import com.glaiss.core.exception.GlaissException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestExceptionHandlerTest {

    private RestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new RestExceptionHandler();
    }

    @Nested
    class Dado_uma_excecao_customizada_glaiss {

        @Test
        void entao_deve_retornar_problem_detail_da_excecao() {
            // Dado
            GlaissException exception = new GlaissException() {
                @Override
                public ProblemDetail toProblemDetail() {
                    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Erro customizado");
                }
            };

            // Quando
            ProblemDetail result = handler.handleListaException(exception);

            // Então
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
            assertEquals("Erro customizado", result.getDetail());
        }
    }

    @Nested
    class Dado_uma_excecao_generica {

        @Test
        void entao_deve_retornar_response_entity_com_stack_trace() {
            // Dado
            Exception exception = new RuntimeException("Erro inesperado");

            // Quando
            ResponseEntity<Map<String, Object>> response = handler.handleAll(exception);

            // Então
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNotNull(response.getBody().get("trace"));
            assertEquals("Erro inesperado", response.getBody().get("message"));
        }
    }
}
