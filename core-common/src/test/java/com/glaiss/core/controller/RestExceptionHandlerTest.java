package com.glaiss.core.controller;

import com.glaiss.core.exception.GlaissException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class RestExceptionHandlerTest {

    private RestExceptionHandler handler;

    // Classe para teste de validação
    private static class TestDto {
        private String campo;
        public String getCampo() { return campo; }
        public void setCampo(String campo) { this.campo = campo; }
    }

    @BeforeEach
    void setUp() {
        handler = new RestExceptionHandler();
    }

    @Nested
    class Dado_uma_excecao_customizada_glaiss {

        @Test
        void entao_deve_retornar_problem_detail_da_excecao() {
            // Dado
            GlaissException exception = new GlaissException();

            // Quando
            ProblemDetail result = handler.handleGlaissException(exception);

            // Então
            assertNotNull(result);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatus());
        }
    }

    @Nested
    class Dado_erros_de_validacao {

        @Test
        void entao_deve_retornar_detalhes_dos_parametros_invalidos() {
            // Dado
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new TestDto(), "target");
            bindingResult.rejectValue("campo", "erro", "mensagem de erro");
            MethodArgumentNotValidException exception = new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);

            // Quando
            ProblemDetail result = handler.handleMethodArgumentNotValidException(exception);

            // Então
            assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
            assertNotNull(result.getProperties().get("invalid-params"));
        }
    }

    @Nested
    class Dado_uma_excecao_generica {

        @Test
        void entao_deve_retornar_erro_500() {
            // Dado
            Exception exception = new Exception("Erro genérico");
            ClassCastException castException = new ClassCastException("Cast error");
            HttpMessageNotReadableException readException = new HttpMessageNotReadableException("Read error", mock(org.springframework.http.HttpInputMessage.class));

            // Quando / Então
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), handler.handleGeneralException(exception).getStatus());
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), handler.handleClassCastException(castException).getStatus());
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), handler.handleHttpMessageNotReadableException(readException).getStatus());
        }
    }
}
