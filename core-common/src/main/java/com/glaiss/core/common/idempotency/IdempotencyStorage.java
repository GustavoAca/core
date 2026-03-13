package com.glaiss.core.common.idempotency;

import java.util.Optional;

/**
 * Interface para armazenamento de chaves de idempotência.
 */
public interface IdempotencyStorage {
    /**
     * Tenta obter um resultado cacheado para uma chave.
     */
    Optional<Object> get(String key);

    /**
     * Armazena um resultado associado a uma chave com TTL.
     */
    void save(String key, Object result, long ttlSeconds);
}
