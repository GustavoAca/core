package com.glaiss.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.io.Serializable;

public class RegistroNaoEncontradoException extends GlaissException {
    private final Serializable id;
    private final String registro;

    public RegistroNaoEncontradoException(Serializable id, String registro) {
        this.id = id;
        this.registro = registro;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("Registro não encontrado.");
        pb.setDetail(String.format("%s não encontrado com o id: %d.", registro, id));
        return pb;
    }
}
