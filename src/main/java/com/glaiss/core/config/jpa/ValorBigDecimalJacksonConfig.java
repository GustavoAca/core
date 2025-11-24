package com.glaiss.core.config.jpa;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.glaiss.core.utils.anotacao.ValorBigDecimalAnnotationIntrospector;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValorBigDecimalJacksonConfig {

//    @Bean
//    public ObjectMapper objectMapper(ObjectMapper base) {
//        var original = base.getDeserializationConfig().getAnnotationIntrospector();
//        var custom = new ValorBigDecimalAnnotationIntrospector();
//
//        var pair = AnnotationIntrospector.pair(original, custom);
//
//        return base.setAnnotationIntrospector(pair);
//    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer bigDecimalCustomizer() {
        return builder -> builder.postConfigurer(mapper -> {
            var original = mapper.getDeserializationConfig().getAnnotationIntrospector();
            var custom = new ValorBigDecimalAnnotationIntrospector();

            var pair = AnnotationIntrospector.pair(original, custom);

            mapper.setAnnotationIntrospector(pair);
        });
    }
}
