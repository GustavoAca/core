package com.glaiss.core.config.jpa;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.AuditorAware;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditoriaAwareConfigTest {

    @Nested
    class Dado_uma_configuracao_de_auditoria {

        @Test
        void entao_deve_carregar_o_bean_padrao_se_ausente() {
            // Dado
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AuditoriaAwareConfig.class);

            // Quando
            AuditorAware<String> bean = context.getBean(AuditorAware.class);

            // Então
            assertNotNull(bean);
            assertTrue(bean instanceof DefaultAuditorAware);
            context.close();
        }
    }
}
