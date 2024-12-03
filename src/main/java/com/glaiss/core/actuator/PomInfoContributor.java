package com.glaiss.core.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PomInfoContributor implements InfoContributor {
    @Value("${spring.application.name}")
    private String nomeProjeto;

    @Value("${spring.application.version}")
    private String projectVersion;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("app", Map.of(
                "version", projectVersion,
                "id", nomeProjeto
        ));
    }
}
