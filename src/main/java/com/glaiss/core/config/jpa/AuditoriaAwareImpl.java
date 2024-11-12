package com.glaiss.core.config.jpa;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditoriaAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        return Objects.nonNull(SecurityContextUtils.getUsername()) ?
//                Optional.of(SecurityContextUtils.getUsername()) :
        return Optional.of(System.getenv("MICROSERVICE_NAME"));
    }
}
