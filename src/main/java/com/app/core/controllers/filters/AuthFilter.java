package com.app.core.controllers.filters;

import com.app.core.config.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@WebFilter(urlPatterns = "/api/v1/*")
public class AuthFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean validToken = validateToken(request);

        if (!validToken) {
            log.debug("Invalid token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean validateToken(HttpServletRequest request) {
        List<String> doNotFilterEndpoints = List.of(LOGIN_ENDPOINT, REGISTER_ENDPOINT);
        String path = request.getRequestURI();
        Optional<String> endpoint = doNotFilterEndpoints.stream().filter(path::contains).findFirst();

        if (endpoint.isPresent()) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authorizationHeader.substring(7);

        return !(token == null || JwtUtil.isTokenExpired(token));
    }
}
