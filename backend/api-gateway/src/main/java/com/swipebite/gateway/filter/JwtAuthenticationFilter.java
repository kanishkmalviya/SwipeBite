
package com.swipebite.gateway.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.swipebite.gateway.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n========== GATEWAY JWT FILTER ==========");
        System.out.println("REQUEST URI: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        System.out.println("AUTH HEADER: "
                + (authHeader == null ? "NOT PRESENT" : "PRESENT"));

        // No token: continue to Spring Security authorization rules
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("NO BEARER TOKEN - CONTINUING REQUEST");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extractClaims(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            System.out.println("JWT EMAIL: " + email);
            System.out.println("JWT ROLE: " + role);

            if (email == null || role == null) {
                System.out.println("INVALID JWT CLAIMS");
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid JWT token");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role)));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception e) {
            System.out.println("JWT VALIDATION ERROR: " + e.getMessage());

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT token");
            return;
        }

        System.out.println("JWT FILTER COMPLETED");
        filterChain.doFilter(request, response);
    }
}
