package com.glaiss.core.utils.anotacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValorBigDecimalValidator implements ConstraintValidator<ValorBigDecimal, BigDecimal> {

    private boolean nullable;

    @Override
    public void initialize(ValorBigDecimal constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return nullable;
        }
        return value.scale() <= 2;
    }
}
