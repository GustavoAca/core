package com.glaiss.core.logger;

import jakarta.servlet.*;
import org.slf4j.MDC;

import java.io.IOException;

public class ApiLogginFilter implements Filter {

    private final String apiName;

    public ApiLogginFilter(String apiName) {
        this.apiName = apiName;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put("apiName", apiName);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("apiName");
        }
    }
}
