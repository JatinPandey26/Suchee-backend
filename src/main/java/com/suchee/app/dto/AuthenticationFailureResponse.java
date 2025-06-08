package com.suchee.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationFailureResponse extends AuthenticationResponse {

    boolean isSuccess=false;
    String errorCause;

}
