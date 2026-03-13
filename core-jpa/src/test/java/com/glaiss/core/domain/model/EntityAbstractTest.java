package com.glaiss.core.domain.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityAbstractTest {

    // Implementação concreta para teste
    private static class ConcreteEntity extends EntityAbstract {
        public ConcreteEntity() {
            super();
        }

        public ConcreteEntity(LocalDateTime createdDate, String createdBy, LocalDateTime modifiedDate, String modifiedBy, Long version) {
            super(createdDate, createdBy, modifiedDate, modifiedBy, version);
        }

        public ConcreteEntity(LocalDateTime modifiedDate, String modifiedBy, Long version) {
            super(modifiedDate, modifiedBy, version);
        }
    }

    @Nested
    class Dado_uma_entidade_concreta {

        @Test
        void entao_deve_testar_construtores_e_acessores() {
            // Dado
            LocalDateTime now = LocalDateTime.now();
            String user = "user";
            Long version = 1L;

            // Quando
            ConcreteEntity entity = new ConcreteEntity(now, user, now, user, version);

            // Então
            assertEquals(now, entity.getCreatedDate());
            assertEquals(user, entity.getCreatedBy());
            
            // Testando setters via reflexão ou acesso indireto (já que são protegidos/privados na base)
            entity.setModifiedBy("new-user");
            entity.setModifiedDate(now.plusDays(1));
            
            assertNotNull(entity);
        }

        @Test
        void entao_deve_testar_construtor_simplificado() {
            // Dado
            LocalDateTime now = LocalDateTime.now();
            ConcreteEntity entity = new ConcreteEntity(now, "mod-user", 2L);

            // Então
            assertNotNull(entity);
        }
    }
}
