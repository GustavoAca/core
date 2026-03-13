package com.glaiss.core.controller;

import com.glaiss.core.exception.GlaissException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(GlaissException.class)
    public ProblemDetail handleGlaissException(GlaissException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErros = e.getFieldErrors()
                .stream()
                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                .toList();
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Parâmetros da requisição inválidos");
        pb.setProperty("invalid-params", fieldErros);
        return pb;
    }

    private record InvalidParam(String fieldName, String reason) {
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Erro na desserialização do JSON");
        pb.setDetail(e.getMessage());
        return pb;
    }

    @ExceptionHandler(ClassCastException.class)
    public ProblemDetail handleClassCastException(ClassCastException e) {
        log.error("Erro de conversão de tipos: ", e);
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro de conversão de tipos");
        pb.setDetail("Ocorreu um erro ao processar os dados.");
        return pb;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception e) {
        log.error("Erro Interno não tratado: ", e);
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro Interno do Servidor");
        pb.setDetail("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde ou entre em contato com o suporte.");
        return pb;
    }
}
