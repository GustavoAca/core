package com.glaiss.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.io.Serializable;
import java.util.Objects;

public class RegistroNaoEncontradoException extends GlaissException {
    private Serializable id;
    private String registro;

    public RegistroNaoEncontradoException(Serializable id, String registro) {
        this.id = id;
        this.registro = registro;
    }

    public RegistroNaoEncontradoException(String registro) {
        this.registro = registro;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("Registro não encontrado.");

        if (Objects.isNull(id)) {
            pb.setDetail(registro);
        } else {
            pb.setDetail(String.format("%s não encontrado com o identificador: %d.", registro, id));
        }
        return pb;
    }
}
