package com.glaiss.core.controller;

import org.hibernate.PersistentObjectException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AutoConfiguration
@RestControllerAdvice
public class JpaRestExceptionHandler {

    @ExceptionHandler(PersistentObjectException.class)
    public ProblemDetail handlePersistentObjectException(PersistentObjectException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro ao persistir dados");
        pb.setDetail(e.getMessage());
        return pb;
    }
}
