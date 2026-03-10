package com.glaiss.core.utils.anotacao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BigDecimalDeserializerTest {

    private BigDecimalDeserializer deserializer;
    private JsonParser jsonParser;
    private DeserializationContext context;

    @BeforeEach
    void setUp() {
        deserializer = new BigDecimalDeserializer();
        jsonParser = Mockito.mock(JsonParser.class);
        context = Mockito.mock(DeserializationContext.class);
    }

    @Nested
    class Dado_uma_string_numerica {

        @Test
        void entao_deve_deserializar_e_arredondar_para_duas_casas() throws IOException {
            // Dado
            String valorJson = "10.555";
            when(jsonParser.getText()).thenReturn(valorJson);

            // Quando
            BigDecimal resultado = deserializer.deserialize(jsonParser, context);

            // Então
            assertEquals(new BigDecimal("10.56"), resultado);
        }

        @Test
        void entao_deve_manter_duas_casas_quando_ja_estiver_no_formato() throws IOException {
            // Dado
            String valorJson = "10.50";
            when(jsonParser.getText()).thenReturn(valorJson);

            // Quando
            BigDecimal resultado = deserializer.deserialize(jsonParser, context);

            // Então
            assertEquals(new BigDecimal("10.50"), resultado);
        }
    }
}
