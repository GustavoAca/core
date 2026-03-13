package com.glaiss.core.utils.anotacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValorBigDecimalValidator implements ConstraintValidator<ValorBigDecimal, BigDecimal> {

    private boolean nullable;
    private int fraction;

    @Override
    public void initialize(ValorBigDecimal annotation) {
        this.nullable = annotation.nullable();
        this.fraction = annotation.fraction();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

        if (value == null) {
            return nullable;
        }

        int scale = Math.max(value.scale(), 0);

        return scale <= fraction;
    }
}