package com.glaiss.core.config.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.gustavoacacio.listadecompra.domain.repository.jpa")
@ConditionalOnProperty(name = "jpa.enabled", havingValue = "true")
public class JpaConfig {
}
