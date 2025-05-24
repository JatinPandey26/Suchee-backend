package com.suchee.app.validation.Errors;

public class SimpleValidationError implements ValidationError{

    private String message;

    public SimpleValidationError(String errorMsg){
        this.message = errorMsg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
