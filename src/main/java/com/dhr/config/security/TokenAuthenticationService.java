package com.dhr.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME_14_DAYS = 1_209_600_000;
    private static final String SECRET = "VIHR_SECR3T";
    static final String TOKEN_SCHEME_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

    @Inject
    private UserService userService;

    static void addAuthentication(HttpServletResponse res, String username) throws IOException {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_14_DAYS))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_SCHEME_PREFIX + jwt);
        res.getWriter().write("{\"token\":\"" + jwt + "\",\"username\":\"" + username + "\"}");
    }

    UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        String authorization = request.getParameter("Authorization");
        String user;
        if (token != null || authorization != null) {
            try {
                if (token != null) {
                    user = Jwts.parser()
                            .setSigningKey(SECRET.getBytes())
                            .parseClaimsJws(token.replace(TOKEN_SCHEME_PREFIX, ""))
                            .getBody()
                            .getSubject();
                } else {
                    user = Jwts.parser()
                            .setSigningKey(SECRET.getBytes())
                            .parseClaimsJws(authorization)
                            .getBody()
                            .getSubject();
                }
                userService.loadUserByUsername(user);
            } catch (JwtException | ClassCastException | UsernameNotFoundException e) {
                return null;
            }
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }
}
