package com.glaiss.core.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityContextUtils {

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
}
