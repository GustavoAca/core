package com.glaiss.core.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RedisConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RedisConfiguration.class))
            .withBean(ObjectMapper.class, ObjectMapper::new)
            .withBean(RedisConnectionFactory.class, () -> mock(RedisConnectionFactory.class));

    @Nested
    class Dado_que_a_propriedade_redis_esta_habilitada {

        @Test
        void entao_deve_carregar_os_beans_de_redis() {
            contextRunner.withPropertyValues("spring.redis.enabled=true")
                    .run(context -> {
                        assertThat(context).hasSingleBean(RedisTemplate.class);
                        assertThat(context).hasBean("cacheConfiguration");
                    });
        }
    }

    @Nested
    class Dado_que_a_propriedade_redis_esta_desabilitada {

        @Test
        void entao_nao_deve_carregar_os_beans() {
            contextRunner.withPropertyValues("spring.redis.enabled=false")
                    .run(context -> {
                        assertThat(context).doesNotHaveBean(RedisTemplate.class);
                    });
        }
    }
}
