package com.glaiss.core.common.idempotency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar métodos de Controller que devem ser idempotentes.
 * Exige a presença do header "X-Idempotency-Key" na requisição.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * Tempo de expiração da chave em segundos (default: 1 hora).
     */
    long ttl() default 3600;
}
