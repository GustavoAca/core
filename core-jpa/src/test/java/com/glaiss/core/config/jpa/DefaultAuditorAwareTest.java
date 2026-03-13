package com.glaiss.core.config.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultAuditorAwareTest {

    private DefaultAuditorAware auditorAware;

    @BeforeEach
    void setUp() {
        auditorAware = new DefaultAuditorAware();
    }

    @Nested
    class Dado_que_o_nome_da_aplicacao_esta_configurado {

        @BeforeEach
        void setup() {
            ReflectionTestUtils.setField(auditorAware, "apiName", "minha-api-test");
        }

        @Test
        void entao_deve_retornar_nome_da_api_como_auditor() {
            // Quando
            Optional<String> result = auditorAware.getCurrentAuditor();

            // Então
            assertTrue(result.isPresent());
            assertEquals("minha-api-test", result.get());
        }
    }
}
