package com.glaiss.core.service;

import com.glaiss.core.model.EntityAbstract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public class BaseServiceImpl<E extends EntityAbstract, K extends Serializable, R extends JpaRepository<E, K>> implements BaseService<E, K> {

    protected R repo;

    protected BaseServiceImpl(R repo) {
        this.repo = repo;
    }

    public E salvar(E entity) {
        return this.repo.save(entity);
    }

    @Override
    public Page<E> listarPagina(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<E> buscarPorId(K id) {
        return repo.findById(id);
    }

    @Override
    public Boolean deletar(K id) {
        repo.deleteById(id);
        return repo.existsById(id);
    }
}
