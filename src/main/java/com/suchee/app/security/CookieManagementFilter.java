package com.suchee.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CookieManagementFilter extends OncePerRequestFilter {

    private static final String AUTH_TOKEN_COOKIE = "auth_token";
    private static final String AUTH_TOKEN_TYPE_COOKIE = "auth_token_type";

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_TYPE_HEADER = "X-Auth-Type";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = null;
        String authTokenType = null;

        // Extract cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTH_TOKEN_COOKIE.equals(cookie.getName())) {
                    authToken = cookie.getValue();
                } else if (AUTH_TOKEN_TYPE_COOKIE.equals(cookie.getName())) {
                    authTokenType = cookie.getValue();
                }
            }
        }

        // Wrap the request to add headers
        HttpServletRequest wrappedRequest = request;

        if (authToken != null || authTokenType != null) {
            final String finalAuthToken = authToken;
            final String finalAuthTokenType = authTokenType;
            wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if (AUTH_HEADER.equalsIgnoreCase(name) && finalAuthToken != null) {
                        assert finalAuthTokenType != null;

                        if (finalAuthTokenType.equalsIgnoreCase(AuthTokenType.JSON_WEB_TOKEN.getType())) {
                            return "Bearer " + finalAuthToken;
                        }
                    }
                    if (AUTH_TYPE_HEADER.equalsIgnoreCase(name) && finalAuthTokenType != null) {
                        return finalAuthTokenType;
                    }
                    return super.getHeader(name);
                }
            };
        }

        filterChain.doFilter(wrappedRequest, response);
    }
}
