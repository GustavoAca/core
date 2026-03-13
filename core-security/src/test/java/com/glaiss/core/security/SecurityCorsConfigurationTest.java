package com.glaiss.core.security;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SecurityCorsConfigurationTest {

    @Nested
    class Dado_uma_configuracao_de_cors {

        @Test
        void entao_deve_registrar_mapeamento_global() {
            // Dado
            SecurityCorsConfiguration config = new SecurityCorsConfiguration();
            CorsRegistry registry = mock(CorsRegistry.class);
            CorsRegistration registration = mock(CorsRegistration.class);
            
            when(registry.addMapping(anyString())).thenReturn(registration);
            when(registration.allowedOrigins(any())).thenReturn(registration);
            when(registration.allowedMethods(any())).thenReturn(registration);

            // Quando
            config.addCorsMappings(registry);

            // Então
            verify(registry).addMapping("/**");
            verify(registration).allowedOrigins("*");
        }
    }
}
