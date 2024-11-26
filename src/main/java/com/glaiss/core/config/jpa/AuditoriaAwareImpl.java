package com.glaiss.core.config.jpa;

import com.glaiss.core.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Objects;
import java.util.Optional;

public class AuditoriaAwareImpl implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String apiName;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Objects.nonNull(SecurityContextUtils.getUsername()) ?
                Optional.of(SecurityContextUtils.getUsername()) :
                Optional.of(apiName);
    }
}
