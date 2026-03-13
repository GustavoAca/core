package com.glaiss.core.common.builder;

/**
 * Interface funcional para padronizar a criação de builders manuais.
 * 
 * @param <T> O tipo de objeto que será construído pelo builder.
 */
@FunctionalInterface
public interface IBuilder<T> {
    
    /**
     * Finaliza a construção e retorna a instância do objeto.
     * 
     * @return Uma nova instância de T.
     */
    T build();
}
