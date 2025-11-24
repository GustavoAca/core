package com.glaiss.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PermissaoFuncionalidadeException extends GlaissException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pb.setTitle("Usuário sem privilegios necessários");
        pb.setDetail("Usuário não contem as permissões necessárias para acessar a funcionalidade");
        return pb;
    }
}
