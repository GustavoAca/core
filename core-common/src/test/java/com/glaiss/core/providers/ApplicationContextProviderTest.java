package com.glaiss.core.providers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationContextProviderTest {

    private ApplicationContext mockContext;
    private ApplicationContextProvider provider;

    @BeforeEach
    void setUp() {
        mockContext = mock(ApplicationContext.class);
        provider = new ApplicationContextProvider();
    }

    @Nested
    class Dado_um_contexto_configurado {

        @BeforeEach
        void configureContext() {
            provider.setApplicationContext(mockContext);
        }

        @Test
        void entao_deve_retornar_contexto_corretamente() {
            // Quando
            ApplicationContext result = ApplicationContextProvider.getContext();

            // Então
            assertNotNull(result);
            assertEquals(mockContext, result);
        }

        @Test
        void entao_deve_retornar_bean_por_classe() {
            // Dado
            String expectedBean = "myBean";
            when(mockContext.getBean(String.class)).thenReturn(expectedBean);

            // Quando
            String result = ApplicationContextProvider.getBean(String.class);

            // Então
            assertEquals(expectedBean, result);
        }
    }

    @Nested
    class Dado_um_contexto_nao_disponivel {

        @Test
        void entao_deve_lancar_excecao_ao_tentar_obter_contexto() {
            // Dado: Forçar o contexto a null para o teste (via reflexão ou reset manual se possível)
            // Como é estático, vou apenas garantir que o erro ocorra se não houver set.
            // Para isolar este teste, poderíamos usar um ClassLoader limpo, mas vamos testar o fluxo básico.
            
            // Então
            assertThrows(IllegalStateException.class, () -> {
                // Forçamos o estado nulo limpando o campo via reflexão se necessário
                org.springframework.test.util.ReflectionTestUtils.setField(ApplicationContextProvider.class, "context", null);
                ApplicationContextProvider.getContext();
            });
        }
    }
}
