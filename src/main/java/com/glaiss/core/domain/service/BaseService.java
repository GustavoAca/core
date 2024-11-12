package com.glaiss.core.domain.service;

import com.glaiss.core.domain.model.EntityAbstract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService<E extends EntityAbstract, K extends Serializable> {
    E salvar(E entity);

    Page<E> listarPagina(Pageable pageable);

    Optional<E> buscarPorId(K id);

    Boolean deletar(K id);
}
