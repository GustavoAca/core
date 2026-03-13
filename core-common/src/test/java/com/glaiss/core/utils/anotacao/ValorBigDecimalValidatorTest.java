package com.glaiss.core.utils.anotacao;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValorBigDecimalValidatorTest {

    private ValorBigDecimalValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new ValorBigDecimalValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Nested
    class Dado_configuracao_nao_nula {

        @BeforeEach
        void init() {
            ValorBigDecimal annotation = mock(ValorBigDecimal.class);
            when(annotation.nullable()).thenReturn(false);
            validator.initialize(annotation);
        }

        @Test
        void entao_deve_invalidar_valor_nulo() {
            assertFalse(validator.isValid(null, context));
        }

        @Test
        void entao_deve_validar_escala_correta() {
            assertTrue(validator.isValid(new BigDecimal("10.50"), context));
            assertTrue(validator.isValid(new BigDecimal("10.5"), context));
            assertTrue(validator.isValid(new BigDecimal("10"), context));
        }

        @Test
        void entao_deve_invalidar_escala_excessiva() {
            assertFalse(validator.isValid(new BigDecimal("10.555"), context));
        }
    }

    @Nested
    class Dado_configuracao_nula_permitida {

        @BeforeEach
        void init() {
            ValorBigDecimal annotation = mock(ValorBigDecimal.class);
            when(annotation.nullable()).thenReturn(true);
            validator.initialize(annotation);
        }

        @Test
        void entao_deve_validar_valor_nulo() {
            assertTrue(validator.isValid(null, context));
        }
    }
}
