package com.glaiss.core.config.eureka;

import com.glaiss.core.domain.Servicos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CorsDiscoveryClientConfigurationTest {

    private DiscoveryClient discoveryClient;
    private CorsDiscoveryClientConfiguration configuration;
    private CorsRegistry registry;
    private CorsRegistration registration;

    @BeforeEach
    void setUp() {
        discoveryClient = mock(DiscoveryClient.class);
        configuration = new CorsDiscoveryClientConfiguration(discoveryClient);
        registry = mock(CorsRegistry.class);
        
        // Criando um mock que retorna a si mesmo para qualquer método que retorne CorsRegistration
        registration = mock(CorsRegistration.class, invocation -> {
            if (invocation.getMethod().getReturnType().isAssignableFrom(CorsRegistration.class)) {
                return invocation.getMock();
            }
            return null;
        });
        
        when(registry.addMapping(anyString())).thenReturn(registration);
    }

    @Nested
    class Dado_que_existem_instancias_do_gateway {

        @Test
        void entao_deve_configurar_cors_com_origens_do_discovery() {
            // Dado
            ServiceInstance instance = mock(ServiceInstance.class);
            when(instance.getHost()).thenReturn("gateway-host");
            when(instance.getPort()).thenReturn(8080);
            when(discoveryClient.getInstances(Servicos.GATEWAY.name())).thenReturn(List.of(instance));

            // Quando
            configuration.addCorsMappings(registry);

            // Então
            verify(registry).addMapping("/**");
            verify(registration).allowedOrigins(new String[]{"gateway-host:8080", "http://localhost:8761"});
            verify(registration).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
        }
    }

    @Nested
    class Dado_que_nao_existem_instancias_do_gateway {

        @Test
        void entao_deve_configurar_apenas_origem_default() {
            // Dado
            when(discoveryClient.getInstances(Servicos.GATEWAY.name())).thenReturn(Collections.emptyList());

            // Quando
            configuration.addCorsMappings(registry);

            // Então
            verify(registry).addMapping("/**");
            verify(registration).allowedOrigins(new String[]{"http://localhost:8761"});
        }
    }
}
