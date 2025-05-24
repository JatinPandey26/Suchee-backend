package com.suchee.app.exception;

public class ValidationException extends AppException{
    public ValidationException(String message) {
        super(message, ERROR_CODE.VALIDATION_ERROR);
    }
}
