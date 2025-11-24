package com.glaiss.core.utils.anotacao;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class ValorBigDecimalAnnotationIntrospector extends NopAnnotationIntrospector {

    @Override
    public Object findDeserializer(Annotated annotated) {
        if (annotated.hasAnnotation(ValorBigDecimal.class)) {
            return BigDecimalDeserializer.class;
        }
        return null;
    }
}
