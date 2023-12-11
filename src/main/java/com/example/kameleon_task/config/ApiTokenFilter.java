package com.example.kameleon_task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class ApiTokenFilter extends OncePerRequestFilter {

    private final String expectedToken;

    public ApiTokenFilter(@Value("${api.token}") String expectedToken) {
        this.expectedToken = expectedToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String providedToken = request.getHeader("Authorization");

        if (isValidToken(providedToken, expectedToken)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isValidToken(String providedToken, String expectedToken) {
        return providedToken != null && providedToken.equals("Bearer " + expectedToken);
    }
}
