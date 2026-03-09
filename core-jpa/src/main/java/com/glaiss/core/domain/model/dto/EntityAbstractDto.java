package com.glaiss.core.domain.model.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class EntityAbstractDto {

    private LocalDateTime modifiedDate;
    private String modifiedBy;
    private LocalDateTime createdDate;
    private String createdBy;
}
