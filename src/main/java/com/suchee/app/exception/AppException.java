package com.suchee.app.exception;

public class AppException extends RuntimeException{
    private final String errorCode;

    public enum ERROR_CODE {
        RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
        VALIDATION_ERROR("VALIDATION_ERROR");

        private final String code;

        ERROR_CODE(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public AppException(String message, ERROR_CODE code) {
        super(message);
        this.errorCode = code.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
