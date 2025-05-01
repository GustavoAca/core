package com.glaiss.core.config.jpa;

import com.glaiss.core.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuditoriaAwareImpl implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String apiName;

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
