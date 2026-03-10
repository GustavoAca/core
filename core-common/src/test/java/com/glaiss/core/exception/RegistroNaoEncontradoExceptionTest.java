package com.glaiss.core.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistroNaoEncontradoExceptionTest {

    @Nested
    class Dado_uma_excecao_com_id {

        @Test
        void entao_deve_retornar_problem_detail_com_detalhe_formatado() {
            // Dado
            var exception = new RegistroNaoEncontradoException(123L, "Usuário");

            // Quando
            ProblemDetail pb = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.NOT_FOUND.value(), pb.getStatus());
            assertEquals("Registro não encontrado.", pb.getTitle());
            assertEquals("Usuário não encontrado com o identificador: 123.", pb.getDetail());
        }
    }

    @Nested
    class Dado_uma_excecao_sem_id {

        @Test
        void entao_deve_retornar_problem_detail_com_mensagem_direta() {
            // Dado
            var exception = new RegistroNaoEncontradoException("Nenhum item encontrado na lista.");

            // Quando
            ProblemDetail pb = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.NOT_FOUND.value(), pb.getStatus());
            assertEquals("Nenhum item encontrado na lista.", pb.getDetail());
        }
    }
}
