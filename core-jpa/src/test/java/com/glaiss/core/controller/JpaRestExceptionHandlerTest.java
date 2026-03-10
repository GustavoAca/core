package com.glaiss.core.controller;

import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JpaRestExceptionHandlerTest {

    private JpaRestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new JpaRestExceptionHandler();
    }

    @Nested
    class Dado_uma_excecao_de_objeto_persistente {

        @Test
        void entao_deve_retornar_erro_500_com_detalhe() {
            // Dado
            var exception = new PersistentObjectException("Erro de persistência");

            // Quando
            ProblemDetail pb = handler.handlePersistentObjectException(exception);

            // Então
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), pb.getStatus());
            assertEquals("Erro ao persistir dados", pb.getTitle());
            assertEquals("Erro de persistência", pb.getDetail());
        }
    }
}
