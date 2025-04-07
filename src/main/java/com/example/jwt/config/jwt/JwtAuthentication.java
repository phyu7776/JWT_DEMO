package com.example.jwt.config.jwt;

import com.example.jwt.service.user.UserVO;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Object userId;
    private Object credentials;
    private UserVO user;

    public JwtAuthentication(UserVO user, Object userInfo, Object credentials,
                             Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.userId = userInfo;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public UserVO getPrincipal() {
        return this.user;
    }
}
