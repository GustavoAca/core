package com.glaiss.core.config.jpa;

import com.glaiss.core.utils.SecurityContextUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class AuditoriaAwareImplTest {

    private AuditoriaAwareImpl auditoriaAware;
    private MockedStatic<SecurityContextUtils> mockedSecurityUtils;

    @BeforeEach
    void setUp() {
        auditoriaAware = new AuditoriaAwareImpl();
        ReflectionTestUtils.setField(auditoriaAware, "apiName", "test-api");
        mockedSecurityUtils = mockStatic(SecurityContextUtils.class);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityUtils.close();
    }

    @Nested
    class Dado_um_usuario_autenticado {

        @Test
        void entao_deve_retornar_o_username() {
            // Dado
            mockedSecurityUtils.when(SecurityContextUtils::getUsername).thenReturn("user-test");

            // Quando
            Optional<String> result = auditoriaAware.getCurrentAuditor();

            // Então
            assertEquals(Optional.of("user-test"), result);
        }
    }

    @Nested
    class Dado_um_usuario_nao_autenticado {

        @Test
        void entao_deve_retornar_o_nome_da_api() {
            // Dado
            mockedSecurityUtils.when(SecurityContextUtils::getUsername).thenReturn(null);

            // Quando
            Optional<String> result = auditoriaAware.getCurrentAuditor();

            // Então
            assertEquals(Optional.of("test-api"), result);
        }
    }

    @Nested
    class Dado_um_erro_ao_obter_contexto {

        @Test
        void entao_deve_retornar_o_nome_da_api_como_fallback() {
            // Dado
            mockedSecurityUtils.when(SecurityContextUtils::getUsername).thenThrow(new RuntimeException("Context error"));

            // Quando
            Optional<String> result = auditoriaAware.getCurrentAuditor();

            // Então
            assertEquals(Optional.of("test-api"), result);
        }
    }
}
