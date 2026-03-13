package com.glaiss.core.utils.anotacao;

import com.fasterxml.jackson.databind.introspect.Annotated;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValorBigDecimalAnnotationIntrospectorTest {

    @Nested
    class Dado_um_campo_anotado {

        @Test
        void entao_deve_retornar_o_deserializador_correto() {
            // Dado
            ValorBigDecimalAnnotationIntrospector introspector = new ValorBigDecimalAnnotationIntrospector();
            Annotated annotated = mock(Annotated.class);
            when(annotated.hasAnnotation(ValorBigDecimal.class)).thenReturn(true);

            // Quando
            Object result = introspector.findDeserializer(annotated);

            // Então
            assertEquals(BigDecimalDeserializer.class, result);
        }
    }

    @Nested
    class Dado_um_campo_nao_anotado {

        @Test
        void entao_deve_retornar_null() {
            // Dado
            ValorBigDecimalAnnotationIntrospector introspector = new ValorBigDecimalAnnotationIntrospector();
            Annotated annotated = mock(Annotated.class);
            when(annotated.hasAnnotation(ValorBigDecimal.class)).thenReturn(false);

            // Quando
            Object result = introspector.findDeserializer(annotated);

            // Então
            assertNull(result);
        }
    }
}
