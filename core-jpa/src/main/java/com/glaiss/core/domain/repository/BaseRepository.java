package com.glaiss.core.domain.repository;

import com.glaiss.core.domain.model.EntityAbstract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface BaseRepository<E extends EntityAbstract, K extends Serializable> extends JpaRepository<E, K> {
}
