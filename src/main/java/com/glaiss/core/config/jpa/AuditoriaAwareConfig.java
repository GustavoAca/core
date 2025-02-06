package com.glaiss.core.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditoriaAwareConfig {

    private final AuditorAware<String> auditorAware;

    public AuditoriaAwareConfig(AuditorAware<String> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return auditorAware;
    }
}
