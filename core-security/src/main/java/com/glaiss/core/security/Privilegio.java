package com.glaiss.core.security;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Privilegio {
    ROLE_ADMIN,
    ROLE_PREMIUM,
    ROLE_BASIC,
    ROLE_FREE;

    Privilegio(){}

    public static List<SimpleGrantedAuthority> getHierarquia(String privilegioUsuario) {
        return Arrays.stream(values())
                .map(Enum::name)
                .dropWhile(privilegio -> !privilegio.equals(privilegioUsuario))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}