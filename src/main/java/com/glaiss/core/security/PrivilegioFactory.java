package com.glaiss.core.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class PrivilegioFactory {

    public static List<SimpleGrantedAuthority> getHierarquia(){
       return Privilegio.getPrivilegios().stream()
               .map(SimpleGrantedAuthority::new).toList();
    }
}
