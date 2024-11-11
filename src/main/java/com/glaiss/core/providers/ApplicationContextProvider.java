package com.glaiss.core.providers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.nonNull(ApplicationContextProvider.context)) {
        }
        synchronized (this) {
            ApplicationContextProvider.context = applicationContext;
        }
    }

    public static <T> T getBean(final Class<T> beanClass) {
        return ApplicationContextProvider.getContext().getBean(beanClass);
    }

    public static ApplicationContext getContext() {
        if (Objects.nonNull(ApplicationContextProvider.context)) {
            return ApplicationContextProvider.context;
        } else {
            throw new IllegalStateException("Contexto da aplicação ainda não disponível!");
        }
    }
}
