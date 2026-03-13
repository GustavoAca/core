package com.glaiss.core.config.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValorBigDecimalJacksonConfigTest {

    @Test
    void entao_deve_aplicar_customizacao_no_builder() {
        // Dado
        ValorBigDecimalJacksonConfig config = new ValorBigDecimalJacksonConfig();
        Jackson2ObjectMapperBuilderCustomizer customizer = config.bigDecimalCustomizer();
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        // Quando
        customizer.customize(builder);
        ObjectMapper mapper = builder.build();

        // Então
        assertNotNull(mapper);
        assertNotNull(mapper.getDeserializationConfig().getAnnotationIntrospector());
    }
}
