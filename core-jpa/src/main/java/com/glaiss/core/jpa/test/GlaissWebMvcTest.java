package com.glaiss.core.jpa.test;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebMvcTest
@Import(GlaissTestConfig.class)
@ImportAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
@TestPropertySource(properties = {
        "spring.cloud.discovery.enabled=false",
        "spring.jpa.enabled=false",
        "spring.main.allow-bean-definition-overriding=true",
        "jwt.public.key=-----BEGIN PUBLIC KEY-----\\nMFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKj34cnwy9uWfrSnoZGgiWpmInaxWlyS7SmaSajS9RAnWESZSGvS6vVkV6SRuJXv8PlEmSjt9S9GHWqihW9ZpkMCAwEAAQ==\\n-----END PUBLIC KEY-----",
        "jwt.private.key=-----BEGIN PRIVATE KEY-----\\nMIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqPfhyeDL25Z+tKehkaCJamYidrFaXJLtKZpJqNL1ECdYRJlIa9Lq9WRXpJG4le/w+USZKO31L0YdaqKFb1mmQwIDAQABAkB9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9Z9AiEA3333333333333333333333333333333333333333333CiEAy555555555555555555555555555555555555555555CiEAt777777777777777777777777777777777777777777CiEAzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzCiEAwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww=-----END PRIVATE KEY-----"
})
public @interface GlaissWebMvcTest {
    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "properties")
    String[] properties() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "excludeFilters")
    ComponentScan.Filter[] excludeFilters() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "includeFilters")
    ComponentScan.Filter[] includeFilters() default {};
}
