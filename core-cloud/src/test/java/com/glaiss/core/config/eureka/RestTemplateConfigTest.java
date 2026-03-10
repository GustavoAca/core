package com.glaiss.core.config.eureka;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class RestTemplateConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RestTemplateConfig.class));

    @Nested
    class Dado_que_eureka_esta_habilitado {

        @Test
        void entao_deve_carregar_rest_template() {
            contextRunner.withPropertyValues("spring.eureka.enabled=true")
                    .run(context -> {
                        assertThat(context).hasSingleBean(RestTemplate.class);
                    });
        }
    }

    @Nested
    class Dado_que_eureka_esta_desabilitado {

        @Test
        void entao_nao_deve_carregar_rest_template() {
            contextRunner.withPropertyValues("spring.eureka.enabled=false")
                    .run(context -> {
                        assertThat(context).doesNotHaveBean(RestTemplate.class);
                    });
        }
    }
}
