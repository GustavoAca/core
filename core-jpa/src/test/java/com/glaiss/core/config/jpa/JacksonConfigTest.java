package com.glaiss.core.config.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JacksonConfigTest {

    @Nested
    class Dado_uma_configuracao_jackson {

        @Test
        void entao_deve_configurar_object_mapper_com_java_time() {
            // Dado
            JacksonConfig config = new JacksonConfig();

            // Quando
            ObjectMapper mapper = config.objectMapper();

            // Então
            assertNotNull(mapper);
            assertTrue(mapper.canSerialize(LocalDateTime.class));
        }
    }
}
