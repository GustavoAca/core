package com.glaiss.core.domain.service;

import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.domain.repository.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseServiceImplTest {

    // Implementação dummy para testar a classe genérica
    private static class TestEntity extends EntityAbstract {}
    private interface TestRepository extends BaseRepository<TestEntity, UUID> {}
    private static class TestServiceImpl extends BaseServiceImpl<TestEntity, UUID, TestRepository> {
        protected TestServiceImpl(TestRepository repo) {
            super(repo);
        }
    }

    private TestRepository repository;
    private TestServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(TestRepository.class);
        service = new TestServiceImpl(repository);
    }

    @Nested
    class Dado_uma_entidade_valida {

        @Test
        void entao_deve_salvar_com_sucesso() {
            // Dado
            TestEntity entity = new TestEntity();
            when(repository.save(entity)).thenReturn(entity);

            // Quando
            TestEntity result = service.salvar(entity);

            // Então
            assertNotNull(result);
            verify(repository, times(1)).save(entity);
        }
    }

    @Nested
    class Dado_um_id_existente {

        @Test
        void entao_deve_buscar_por_id_com_sucesso() {
            // Dado
            UUID id = UUID.randomUUID();
            TestEntity entity = new TestEntity();
            when(repository.findById(id)).thenReturn(Optional.of(entity));

            // Quando
            Optional<TestEntity> result = service.buscarPorId(id);

            // Então
            assertTrue(result.isPresent());
            assertEquals(entity, result.get());
        }

        @Test
        void entao_deve_deletar_com_sucesso() {
            // Dado
            UUID id = UUID.randomUUID();
            when(repository.existsById(id)).thenReturn(false);

            // Quando
            Boolean result = service.deletar(id);

            // Então
            assertTrue(result);
            verify(repository, times(1)).deleteById(id);
        }
    }
}
