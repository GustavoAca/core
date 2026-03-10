package com.glaiss.core.utils.anotacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ValorBigDecimalValidatorTest {

    private ValorBigDecimalValidator validator;
    private ValorBigDecimal annotation;

    @BeforeEach
    void setUp() {
        validator = new ValorBigDecimalValidator();
        annotation = Mockito.mock(ValorBigDecimal.class);
    }

    @Nested
    class Dado_configuracao_nao_nula {

        @BeforeEach
        void setup() {
            when(annotation.nullable()).thenReturn(false);
            validator.initialize(annotation);
        }

        @Test
        void entao_deve_rejeitar_valor_nulo() {
            assertFalse(validator.isValid(null, null));
        }

        @Test
        void entao_deve_aceitar_valor_com_duas_casas_decimais() {
            assertTrue(validator.isValid(new BigDecimal("10.50"), null));
        }

        @Test
        void entao_deve_rejeitar_valor_com_tres_casas_decimais() {
            assertFalse(validator.isValid(new BigDecimal("10.555"), null));
        }
    }

    @Nested
    class Dado_configuracao_nula_permitida {

        @BeforeEach
        void setup() {
            when(annotation.nullable()).thenReturn(true);
            validator.initialize(annotation);
        }

        @Test
        void entao_deve_aceitar_valor_nulo() {
            assertTrue(validator.isValid(null, null));
        }
    }
}
