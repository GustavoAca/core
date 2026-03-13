package com.glaiss.core.utils;

import com.glaiss.core.providers.ApplicationContextProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityContextUtilsTest {

    private MockedStatic<ApplicationContextProvider> mockedProvider;
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() {
        mockedProvider = mockStatic(ApplicationContextProvider.class);
        jwtDecoder = mock(JwtDecoder.class);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        mockedProvider.close();
        SecurityContextHolder.clearContext();
    }

    @Nested
    class Dado_um_contexto_autenticado {

        @Test
        void entao_deve_extrair_username_com_sucesso() {
            // Dado
            String authName = "username: gustavo, userId: 550e8400-e29b-41d4-a716-446655440000";
            Authentication auth = mock(Authentication.class);
            when(auth.getName()).thenReturn(authName);
            
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(auth);
            SecurityContextHolder.setContext(context);

            // Quando
            String username = SecurityContextUtils.getUsername();

            // Então
            assertEquals("gustavo", username);
        }

        @Test
        void entao_deve_extrair_id_com_sucesso() {
            // Dado
            UUID expectedId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            String authName = "username: gustavo, userId: " + expectedId;
            Authentication auth = mock(Authentication.class);
            when(auth.getName()).thenReturn(authName);
            
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(auth);
            SecurityContextHolder.setContext(context);

            // Quando
            UUID id = SecurityContextUtils.getId();

            // Então
            assertEquals(expectedId, id);
        }

        @Test
        void entao_deve_retornar_null_quando_nao_autenticado() {
            // Dado
            SecurityContextHolder.clearContext();

            // Quando / Então
            assertNull(SecurityContextUtils.getUsername());
            assertNull(SecurityContextUtils.getId());
        }
    }

    @Nested
    class Dado_uma_validacao_de_token {

        @Test
        void entao_deve_validar_token_correto() {
            // Dado
            String token = "valid-token";
            Jwt jwt = mock(Jwt.class);
            URL mockUrl = mock(URL.class);
            when(mockUrl.toString()).thenReturn("GLAISS");
            when(jwt.getIssuer()).thenReturn(mockUrl);
            
            mockedProvider.when(() -> ApplicationContextProvider.getBean(JwtDecoder.class)).thenReturn(jwtDecoder);
            when(jwtDecoder.decode(token)).thenReturn(jwt);

            // Quando
            boolean result = SecurityContextUtils.validateToken(token);

            // Então
            assertTrue(result);
        }

        @Test
        void entao_deve_retornar_false_para_token_invalido() {
            // Quando / Então
            assertFalse(SecurityContextUtils.validateToken(null));
            assertFalse(SecurityContextUtils.validateToken(""));
        }

        @Test
        void entao_deve_retornar_false_em_caso_de_excecao() {
            // Dado
            String token = "bad-token";
            mockedProvider.when(() -> ApplicationContextProvider.getBean(JwtDecoder.class)).thenReturn(jwtDecoder);
            when(jwtDecoder.decode(token)).thenThrow(new JwtException("Invalid"));

            // Quando
            boolean result = SecurityContextUtils.validateToken(token);

            // Então
            assertFalse(result);
        }
    }

    @Nested
    class Dado_busca_de_email_no_token {

        @Test
        void entao_deve_retornar_null_se_token_invalido() {
            // Quando
            String email = SecurityContextUtils.getEmailFromToken(null);
            // Então
            assertNull(email);
        }

        @Test
        void entao_deve_retornar_claim_username_se_token_valido() {
            // Dado
            String token = "valid-token";
            Jwt jwt = mock(Jwt.class);
            URL mockUrl = mock(URL.class);
            when(mockUrl.toString()).thenReturn("GLAISS");
            when(jwt.getIssuer()).thenReturn(mockUrl);
            when(jwt.getClaimAsString("username")).thenReturn("test@glaiss.com");
            
            mockedProvider.when(() -> ApplicationContextProvider.getBean(JwtDecoder.class)).thenReturn(jwtDecoder);
            when(jwtDecoder.decode(token)).thenReturn(jwt);

            // Quando
            String email = SecurityContextUtils.getEmailFromToken(token);

            // Então
            assertEquals("test@glaiss.com", email);
        }
    }
}
