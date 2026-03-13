package com.glaiss.core.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlaissExceptionTest {

    @Nested
    class Dado_uma_excecao_generica {

        @Test
        void entao_deve_retornar_problem_detail_com_erro_interno() {
            // Dado
            GlaissException exception = new GlaissException();

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatus());
            assertEquals("Erro interno", result.getTitle());
        }
    }

    @Nested
    class Dado_uma_excecao_de_registro_ja_cadastrado {

        @Test
        void entao_deve_retornar_problem_detail_com_conflito() {
            // Dado
            String mensagem = "O registro XPTO já existe";
            RegistroJaCadastradoException exception = new RegistroJaCadastradoException(mensagem);

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.CONFLICT.value(), result.getStatus());
            assertEquals("Registro já cadastrado", result.getTitle());
            assertEquals(mensagem, result.getDetail());
        }
    }

    @Nested
    class Dado_uma_excecao_de_credencial_invalida {

        @Test
        void entao_deve_retornar_problem_detail_com_unauthorized() {
            // Dado
            CredencialException exception = new CredencialException();

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getStatus());
            assertEquals("Usuário ou senha inválidos", result.getTitle());
        }
    }

    @Nested
    class Dado_uma_excecao_de_permissao_negada {

        @Test
        void entao_deve_retornar_problem_detail_com_forbidden() {
            // Dado
            PermissaoFuncionalidadeException exception = new PermissaoFuncionalidadeException();

            // Quando
            ProblemDetail result = exception.toProblemDetail();

            // Então
            assertEquals(HttpStatus.FORBIDDEN.value(), result.getStatus());
            assertEquals("Usuário sem privilegios necessários", result.getTitle());
        }
    }
}
