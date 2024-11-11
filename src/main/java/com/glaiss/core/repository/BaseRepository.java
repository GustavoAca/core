package com.glaiss.core.repository;

import com.glaiss.core.model.EntityAbstract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface BaseRepository<E extends EntityAbstract, K extends Serializable> extends JpaRepository<E, K> {
}
