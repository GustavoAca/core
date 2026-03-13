package com.glaiss.core.domain.service;

import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.domain.model.ResponsePage;
import com.glaiss.core.domain.repository.BaseRepository;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

public class BaseServiceImpl<E extends EntityAbstract, K extends Serializable, R extends BaseRepository<E, K>> implements BaseService<E, K> {

    protected R repo;

    protected BaseServiceImpl(R repo) {
        this.repo = repo;
    }

    public E salvar(E entity) {
        return this.repo.save(entity);
    }

    @Override
    public ResponsePage<E> listarPagina(Pageable pageable) {
        return new ResponsePage<>(repo.findAll(pageable));
    }

    @Override
    public Optional<E> buscarPorId(K id) {
        return repo.findById(id);
    }

    @Override
    public Boolean deletar(K id) {
        repo.deleteById(id);
        return !repo.existsById(id);
    }
}
