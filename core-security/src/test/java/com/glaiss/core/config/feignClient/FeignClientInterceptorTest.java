package com.glaiss.core.config.feignClient;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignClientInterceptorTest {

    private FeignClientInterceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new FeignClientInterceptor();
    }

    @Nested
    class Dado_um_usuario_autenticado_via_jwt {

        @Test
        void entao_deve_adicionar_header_authorization() {
            try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
                // Dado
                SecurityContext securityContext = mock(SecurityContext.class);
                Jwt jwt = mock(Jwt.class);
                when(jwt.getTokenValue()).thenReturn("token-123");
                
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                RequestTemplate template = new RequestTemplate();

                // Quando
                interceptor.requestInterceptor().apply(template);

                // Então
                assertThat(template.headers().get("Authorization")).containsExactly("Bearer token-123");
            }
        }
    }

    @Nested
    class Dado_um_usuario_nao_autenticado {

        @Test
        void entao_nao_deve_adicionar_header_authorization() {
            try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
                // Dado
                SecurityContext securityContext = mock(SecurityContext.class);
                when(securityContext.getAuthentication()).thenReturn(null);
                mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                RequestTemplate template = new RequestTemplate();

                // Quando
                interceptor.requestInterceptor().apply(template);

                // Então
                assertThat(template.headers().get("Authorization")).isNull();
            }
        }
    }
}
