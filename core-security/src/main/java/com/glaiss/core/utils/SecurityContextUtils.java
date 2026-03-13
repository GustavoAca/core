package com.glaiss.core.utils;

import com.glaiss.core.providers.ApplicationContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityContextUtils {

    private static final String EXPECTED_ISSUER = "GLAISS";

    private SecurityContextUtils() {
    }

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) ? extractUsername(auth.getName()) : null;
    }

    private static String extractUsername(String input) {
        Pattern pattern = Pattern.compile("username:\\s*([^,\\s]+)");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    public static UUID getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) ? extractId(auth.getName()) : null;
    }

    private static UUID extractId(String input) {
        Pattern pattern = Pattern.compile("userId:\\s*([a-fA-F0-9-]+)");
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? UUID.fromString(matcher.group(1).trim()) : null;
    }

    // Valida o token (assinatura, expiração e issuer quando configurado)
    public static boolean validateToken(String token) {
        if (Objects.isNull(token) || token.isBlank()) return false;
        try {
            JwtDecoder decoder = ApplicationContextProvider.getBean(JwtDecoder.class);
            Jwt jwt = decoder.decode(token);
            if (EXPECTED_ISSUER != null && !EXPECTED_ISSUER.isBlank()) {
                String issuer = jwt.getIssuer() != null ? jwt.getIssuer().toString() : null;
                return EXPECTED_ISSUER.equals(issuer);
            }
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // Extrai email do token; retorna null se inválido ou claim ausente
    public static String getEmailFromToken(String token) {
        if (!validateToken(token)) return null;
        try {
            JwtDecoder decoder = ApplicationContextProvider.getBean(JwtDecoder.class);
            Jwt jwt = decoder.decode(token);
            return jwt.getClaimAsString("username");
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }
}
