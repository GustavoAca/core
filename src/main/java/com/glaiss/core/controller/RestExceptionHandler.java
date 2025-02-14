package com.glaiss.core.controller;

import com.glaiss.core.exception.GlaissException;
import org.hibernate.PersistentObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RestExceptionHandler {

    protected RestExceptionHandler() {
    }

    @ExceptionHandler(GlaissException.class)
    public ProblemDetail handleListaException(GlaissException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErros = e.getFieldErrors()
                .stream()
                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                .toList();
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Parametros da requisição não estão validados");
        pb.setProperty("invalid-params", fieldErros);
        return pb;
    }

    private record InvalidParam(String fieldName, String reason) {
    }

    @ExceptionHandler(PersistentObjectException.class)
    public ProblemDetail handlePersistentObjectException(PersistentObjectException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro ao persistir dados");
        return pb;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handlerCannotDeserialize(HttpMessageNotReadableException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Erro ao deserializar UUID");
        pb.setDetail(e.getMessage());
        return pb;
    }

    @ExceptionHandler(ClassCastException.class)
    public ProblemDetail castCannotParse(ClassCastException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro ao trasformar objeto");
        pb.setDetail(e.getMessage());
        return pb;
    }

    @ExceptionHandler(Throwable.class)
    public ProblemDetail sqlException(Throwable e) {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro");
        pb.setDetail(e.getMessage());
        return pb;
    }
}

