package com.glaiss.core.security;

import com.glaiss.core.config.CorsConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@ConditionalOnProperty(name = "spring.security.cors.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityCorsConfiguration implements CorsConfig {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}
