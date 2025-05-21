// src/main/java/com/example/project_management/security/JwtAuthenticationFilter.java
package com.example.project_management.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getAllClaims(token);

                Long userId = claims.get("userId", Long.class);
                String username = claims.getSubject();
                String name = claims.get("name", String.class);
                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("roles", List.class);

                var authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // rebuild your principal with ID and name from claims
                MyUserDetails principal
                        = new MyUserDetails(userId, username, /* password */ "",
                                name, authorities);

                var authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
