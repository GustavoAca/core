package com.glaiss.core.logger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class ApiLogginFilter extends OncePerRequestFilter {

    private final String apiName;

    public ApiLogginFilter(String apiName) {
        this.apiName = apiName;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();

        try {
            MDC.put("service.name", apiName);
            MDC.put("trace.id", traceId);
            MDC.put("http.request.method", request.getMethod());
            MDC.put("url.path", request.getRequestURI());

            chain.doFilter(request, response);

        } finally {
            long duration = System.currentTimeMillis() - start;

            MDC.put("http.response.status_code", String.valueOf(response.getStatus()));
            MDC.put("event.duration", String.valueOf(duration * 1_000_000)); // nanos (ECS)

            if (response.getStatus() >= 500) {
                log.error("HTTP request failed");
            } else if (duration > 1000) {
                log.warn("Slow HTTP request");
            }

            MDC.clear();
        }
    }
}

