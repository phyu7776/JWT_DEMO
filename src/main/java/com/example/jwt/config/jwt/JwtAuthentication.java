package com.example.jwt.config.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Object userId;
    private Object credentials;

    public JwtAuthentication(Object userInfo, Object credentials,
                             Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userInfo;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


    @Override
    public Object getPrincipal() {
        return userId;
    }
}
