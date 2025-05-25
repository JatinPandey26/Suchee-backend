package com.suchee.app.security;

import com.suchee.app.logging.LogLevel;
import com.suchee.app.logging.Trace;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Implementation of {@link AuthenticationTokenGenerator} that creates
 * HTTP Basic Authentication tokens.
 *
 * <p>
 * A Basic Auth token is a Base64-encoded string of the format {@code username:password}.
 * This method of authentication does not support token expiration or additional claims.
 * </p>
 */
@Component
public class BasicAuthenticationTokenGenerator implements AuthenticationTokenGenerator{

    @Override
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();

        String auth = username+":"+password;

        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public String generateToken(UserDetails userDetails,String clearPassword) {
        String username = userDetails.getUsername();
        String password = clearPassword;

        String auth = username+":"+password;

        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    @Override
    public boolean isTokenExpired(String token) {
        // Basic Auth token has no expiry date
        // as it has only username:password
        return false;
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        String expectedToken = generateToken(userDetails);
        return expectedToken.equals(token);    }

    @Override
    public String extractUsername(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split(":")[0];
        } catch (Exception e) {
            e.printStackTrace();
            Trace.log(LogLevel.ERROR,e.getMessage());
            return null;
        }
    }

    @Override
    public AuthTokenType getAuthTokenType() {
        return AuthTokenType.BASIC_AUTH;
    }
}
