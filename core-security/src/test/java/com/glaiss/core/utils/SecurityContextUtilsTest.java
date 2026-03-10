package com.glaiss.core.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class SecurityContextUtilsTest {

    private SecurityContext securityContext;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);
    }

    @Nested
    class Dado_um_contexto_autenticado {

        @Test
        void entao_deve_extrair_username_corretamente() {
            try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
                // Dado
                String nameFormat = "username: gustavo, userId: " + UUID.randomUUID();
                when(authentication.getName()).thenReturn(nameFormat);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                // Quando
                String username = SecurityContextUtils.getUsername();

                // Então
                assertEquals("gustavo", username);
            }
        }

        @Test
        void entao_deve_extrair_id_corretamente() {
            try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
                // Dado
                UUID expectedId = UUID.randomUUID();
                String nameFormat = "username: gustavo, userId: " + expectedId;
                when(authentication.getName()).thenReturn(nameFormat);
                when(securityContext.getAuthentication()).thenReturn(authentication);
                mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);

                // Quando
                UUID resultId = SecurityContextUtils.getId();

                // Então
                assertEquals(expectedId, resultId);
            }
        }
    }

    @Nested
    class Dado_um_contexto_nao_autenticado {

        @Test
        void entao_deve_retornar_nulo_para_username() {
            try (MockedStatic<SecurityContextHolder> mockedContext = Mockito.mockStatic(SecurityContextHolder.class)) {
                // Dado
                mockedContext.when(SecurityContextHolder::getContext).thenReturn(securityContext);
                when(securityContext.getAuthentication()).thenReturn(null);

                // Quando & Então
                assertNull(SecurityContextUtils.getUsername());
            }
        }
    }
}
