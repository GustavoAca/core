package com.glaiss.core.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@ConditionalOnProperty(name = "spring.security.enabled", havingValue = "true")
public class JwtAuthentication {

    public static JwtAuthenticationConverter converter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            String scope = jwt.getClaimAsString("authorities");

            if (scope != null) {
                authorities.addAll(Privilegio.getHierarquia(scope));
            }
            return authorities;
        });
        return jwtAuthenticationConverter;
    }
}
