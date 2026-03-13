package com.glaiss.core.common.idempotency;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * Aspecto que intercepta métodos anotados com @Idempotent.
 * Verifica o header "X-Idempotency-Key" e gerencia o cache de resultados.
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class IdempotencyAspect {

    private static final String IDEMPOTENCY_HEADER = "X-Idempotency-Key";
    private final IdempotencyStorage idempotencyStorage;

    @Around("@annotation(idempotentAnnotation)")
    public Object handleIdempotency(ProceedingJoinPoint joinPoint, Idempotent idempotentAnnotation) throws Throwable {
        HttpServletRequest request = getRequest();
        String key = request.getHeader(IDEMPOTENCY_HEADER);

        // Se a chave não estiver presente, apenas prossegue (opcional: lançar erro 400)
        if (key == null || key.isBlank()) {
            log.warn("Método idempotente chamado sem o header {}", IDEMPOTENCY_HEADER);
            return joinPoint.proceed();
        }

        // Gera uma chave única baseada no endpoint para evitar conflitos globais
        String uniqueKey = String.format("idempotency:%s:%s", request.getRequestURI(), key);

        // 1. Verifica no cache
        Optional<Object> cachedResponse = idempotencyStorage.get(uniqueKey);
        if (cachedResponse.isPresent()) {
            log.info("Resposta retornada do cache de idempotência para chave: {}", key);
            return cachedResponse.get();
        }

        // 2. Executa o método
        Object result = joinPoint.proceed();

        // 3. Salva no cache
        idempotencyStorage.save(uniqueKey, result, idempotentAnnotation.ttl());
        log.debug("Resultado da operação salvo no cache de idempotência: {}", key);

        return result;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("Não foi possível obter o contexto da requisição HTTP.");
        }
        return attributes.getRequest();
    }
}
