package com.glaiss.core.config.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Auditor padrão que utiliza o nome da aplicação.
 * Será substituído pela implementação do core-security se presente.
 */
public class DefaultAuditorAware implements AuditorAware<String> {

    @Value("${spring.application.name:system}")
    private String apiName;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(apiName);
    }
}
