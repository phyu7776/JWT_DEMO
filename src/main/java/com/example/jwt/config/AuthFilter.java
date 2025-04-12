package com.example.jwt.config;

import com.example.jwt.config.jwt.JwtAuthentication;
import com.example.jwt.config.jwt.JwtTokenProvider;
import com.example.jwt.utils.LettuceUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final LettuceUtil lettuceUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.getToken(request);

        if (!ObjectUtils.isEmpty(token) && jwtTokenProvider.validateToken(token)) {

            JwtAuthentication authentication = new JwtAuthentication(
                    lettuceUtil.getUser(token)
                    , jwtTokenProvider.getUserId(token)
                    , null
                    , List.of(new SimpleGrantedAuthority("ROLE_" + jwtTokenProvider.getRole(token))));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}
