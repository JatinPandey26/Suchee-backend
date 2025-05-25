package com.suchee.app.dto;

import com.suchee.app.security.AuthTokenType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationSuccessResponse extends AuthenticationResponse{

    private LocalDateTime timestamp;
    boolean isSuccess = true;

    // Basic,jwt etc
    String authToken;
    AuthTokenType authTokenType;

}
