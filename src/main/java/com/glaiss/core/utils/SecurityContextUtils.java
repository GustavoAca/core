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
        Pattern pattern = Pattern.compile("username=([^,\\]]+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    public static UUID getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) ? extractId(auth.getName()) : null;
    }

    private static UUID extractId(String input) {
        Pattern pattern = Pattern.compile("userId=([^,\\]]+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return UUID.fromString(matcher.group(1).trim());
        }
        return null;
    }
}
