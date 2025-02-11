package com.glaiss.core.security;

import com.glaiss.core.domain.Servicos;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@Configuration
public class CorsConfigurationAbstract implements WebMvcConfigurer {

    private final DiscoveryClient discoveryClient;

    public CorsConfigurationAbstract(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(getAllowedOrigins())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }

    private String[] getAllowedOrigins() {
        List<String> origins = discoveryClient.getInstances(Servicos.GATEWAY.name()).stream()
                .map(g -> String.format("%s:%s", g.getHost(), g.getPort()))
                .collect(Collectors.toList());
        origins.add("http://localhost:8761");
        return origins.toArray(new String[0]);
    }
}
