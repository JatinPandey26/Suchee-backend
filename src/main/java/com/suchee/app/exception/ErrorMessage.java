package com.suchee.app.exception;

public class ErrorMessage{

    public static final String INVALID_USERNAME_PASSWORD = "Invalid username or password.";
    public static final String METHOD_ARGUMENT_NOT_VALID = "Invalid request. Some fields failed validation.";
    public static final String RESOURCE_NOT_FOUND = "The requested resource was not found.";
    public static final String VALIDATION_ERROR = "Validation error occurred.";
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred. Please try again later.";
    public static final String ACCESS_DENIED = "You do not have permission to perform this action.";
    public static final String UNAUTHORIZED = "Authentication is required or has failed.";

    private ErrorMessage() {
        // Prevent instantiation
    }
}
