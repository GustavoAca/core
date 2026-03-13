package com.glaiss.core.utils.anotacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BigDecimalDeserializerTest {

    @Nested
    class Dado_uma_string_numerica {

        @Test
        void entao_deve_deserializar_com_escala_correta() throws IOException {
            // Dado
            BigDecimalDeserializer deserializer = new BigDecimalDeserializer();
            JsonParser p = mock(JsonParser.class);
            DeserializationContext ctxt = mock(DeserializationContext.class);
            
            when(p.getText()).thenReturn("100.5");

            // Quando
            BigDecimal result = deserializer.deserialize(p, ctxt);

            // Então
            assertEquals(new BigDecimal("100.50"), result);
            assertEquals(2, result.scale());
        }
    }
}
