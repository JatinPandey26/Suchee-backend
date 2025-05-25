package com.suchee.app.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface for generating, validating, and extracting information from authentication tokens.
 * This abstraction allows for different types of token strategies (e.g., JWT, opaque tokens).
 */

public interface AuthenticationTokenGenerator {

    /**
     * Generates a new token for the current authenticated user or a given principal.
     */
    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails , String clearPassword);

    /**
     * Validates the provided token and returns whether it's expired.
     */
    boolean isTokenExpired(String token);

    /**
     * Validates the token's signature and structure.
     */
    boolean isValid(String token, UserDetails userDetails);

    /**
     * Extracts the username (or subject) from the token.
     */
    String extractUsername(String token);

    AuthTokenType getAuthTokenType();
}
