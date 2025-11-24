package com.glaiss.core.utils.anotacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValorBigDecimalValidator implements ConstraintValidator<ValorBigDecimal, BigDecimal> {

    private String pattern;

    @Override
    public void initialize(ValorBigDecimal constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.scale() <= 2;
    }
}
