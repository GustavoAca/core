package com.glaiss.core.config.jpa;

import com.glaiss.core.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

import java.util.Objects;
import java.util.Optional;

@AutoConfiguration
@ConditionalOnClass(AuditorAware.class)
public class AuditoriaAwareImpl implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String apiName;

    @Bean
    @Primary
    public AuditorAware<String> auditorProvider() {
        return this;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Objects.nonNull(SecurityContextUtils.getUsername()) ?
                    Optional.of(SecurityContextUtils.getUsername()) :
                    Optional.of(apiName);
        } catch (Throwable e) {
            return Optional.of(apiName);
        }
    }
}
