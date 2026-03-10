package com.glaiss.core.domain.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class EntityAbstract extends EntityCreatedAbstract {

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Version
    private Long version;

    @Access(AccessType.PROPERTY)
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Access(AccessType.PROPERTY)
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    protected EntityAbstract() {
    }

    public EntityAbstract(LocalDateTime modifiedDate, String modifiedBy, Long version) {
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public EntityAbstract(LocalDateTime createdDate, String createdBy, LocalDateTime modifiedDate, String modifiedBy, Long version) {
        super(createdDate, createdBy);
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }
}
