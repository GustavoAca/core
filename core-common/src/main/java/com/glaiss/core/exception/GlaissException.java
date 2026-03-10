package com.glaiss.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class GlaissException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro interno");
        return pb;
    }
}
