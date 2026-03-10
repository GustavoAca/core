package com.glaiss.core.security;

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

    @Nested
    class Dado_um_jwt_com_authorities {

        @Test
        void entao_deve_converter_para_granted_authorities() {
            // Dado
            Jwt jwt = mock(Jwt.class);
            // Simula um privilégio que existe no enum Privilegio (ex: ADMIN ou similar)
            // Como não vi o conteúdo exato do Enum, vou usar uma string genérica e assumir que o Enum trata.
            when(jwt.getClaimAsString("authorities")).thenReturn("ROLE_ADMIN");
            
            JwtAuthenticationConverter converter = JwtAuthentication.converter();

            // Quando
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) converter.convert(jwt).getAuthorities();

            // Então
            assertFalse(authorities.isEmpty());
        }
    }

    @Nested
    class Dado_um_jwt_sem_authorities {

        @Test
        void entao_deve_retornar_lista_vazia() {
            // Dado
            Jwt jwt = mock(Jwt.class);
            when(jwt.getClaimAsString("authorities")).thenReturn(null);
            
            JwtAuthenticationConverter converter = JwtAuthentication.converter();

            // Quando
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) converter.convert(jwt).getAuthorities();

            // Então
            assertTrue(authorities.isEmpty());
        }
    }
}
