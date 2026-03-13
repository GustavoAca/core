package com.glaiss.core.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistroNaoEncontradoExceptionTest {

    @Nested
    class Dado_uma_excecao_com_id {

        @Test
        void entao_deve_retornar_detalhe_com_id() {
            // Dado
            Long id = 123L;
            String registro = "Usuario";
            RegistroNaoEncontradoException exception = new RegistroNaoEncontradoException(id, registro);

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
            assertTrue(result.getDetail().contains("123"));
            assertTrue(result.getDetail().contains("Usuario"));
        }
    }

    @Nested
    class Dado_uma_excecao_sem_id {

        @Test
        void entao_deve_retornar_detalhe_apenas_com_registro() {
            // Dado
            String registro = "Lista de Compras";
            RegistroNaoEncontradoException exception = new RegistroNaoEncontradoException(registro);

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
            assertEquals(registro, result.getDetail());
        }
    }
}
