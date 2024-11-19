package com.glaiss.core.security;


import java.util.*;

public enum Privilegio {
    ROLE_FREE,
    ROLE_BASIC,
    ROLE_PREMIUM,
    ROLE_ADMIN;

    Privilegio(){}

    public static List<String> getPrivilegios(){
        return Arrays.stream(values())
                .map(Enum::name)
                .toList();
    }
}