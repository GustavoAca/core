package com.glaiss.core.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtAuthenticationTest {

    private JwtAuthentication jwtAuthentication;

    @BeforeEach
    void setUp() {
        jwtAuthentication = new JwtAuthentication();
    }

    @Nested
    class Dado_um_jwt_com_authorities {

        @Test
        @SuppressWarnings("unchecked")
        void entao_deve_converter_para_granted_authorities() {
            // Dado
            Jwt jwt = mock(Jwt.class);
            when(jwt.getClaimAsString("authorities")).thenReturn("ROLE_ADMIN");

            JwtAuthenticationConverter converter = jwtAuthentication.jwtAuthenticationConverter();

            // Quando
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) converter.convert(jwt).getAuthorities();

            // Então
            assertFalse(authorities.isEmpty());
        }
    }

    @Nested
    class Dado_um_jwt_sem_authorities {

        @Test
        @SuppressWarnings("unchecked")
        void entao_deve_retornar_lista_vazia() {
            // Dado
            Jwt jwt = mock(Jwt.class);
            when(jwt.getClaimAsString("authorities")).thenReturn(null);

            JwtAuthenticationConverter converter = jwtAuthentication.jwtAuthenticationConverter();

            // Quando
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) converter.convert(jwt).getAuthorities();

            // Então
            assertTrue(authorities.isEmpty());
        }
    }
}
