package com.suchee.app.exception;
import lombok.Data;

import java.time.Instant;

@Data
public class ApiError {

    private final Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String message;
    private String path;

    // Constructors, getters, and optionally builder pattern

    public ApiError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters only
}

