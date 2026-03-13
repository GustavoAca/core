package com.glaiss.core.common.idempotency;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Auto-configuração para o mecanismo de idempotência.
 */
@Configuration
public class IdempotencyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IdempotencyStorage.class)
    public IdempotencyStorage inMemoryIdempotencyStorage() {
        return new InMemoryIdempotencyStorage();
    }

    /**
     * Implementação simples em memória usando ConcurrentHashMap.
     */
    public static class InMemoryIdempotencyStorage implements IdempotencyStorage {
        // Map<Chave, Resultado>
        private final Map<String, Object> cache = new ConcurrentHashMap<>();

        @Override
        public Optional<Object> get(String key) {
            return Optional.ofNullable(cache.get(key));
        }

        @Override
        public void save(String key, Object result, long ttlSeconds) {
            cache.put(key, result);
            // Nota: Em memória, o TTL não está implementado para simplificar. 
            // Em produção com Redis (core-cache), o TTL será respeitado.
        }
    }
}
