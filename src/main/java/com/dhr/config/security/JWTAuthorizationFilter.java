package com.dhr.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    JWTAuthorizationFilter(AuthenticationManager authManager, TokenAuthenticationService tokenAuthService) {
        super(authManager);
        this.tokenAuthService = tokenAuthService;
    }

    private TokenAuthenticationService tokenAuthService;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(TokenAuthenticationService.HEADER_STRING);
        if (header == null || !header.startsWith(TokenAuthenticationService.TOKEN_SCHEME_PREFIX)) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied, token is not provided!");
            return;
        }
        UsernamePasswordAuthenticationToken authentication = tokenAuthService.getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}