package com.glaiss.core.utils.anotacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValorBigDecimalValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorBigDecimal {

    String message() default "O valor deve ter no máximo {fraction} casas decimais";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int fraction() default 2;

    boolean nullable() default false;
}
