package com.suchee.app.exception;

public class ResourceAlreadyExistsException extends  AppException {
    public ResourceAlreadyExistsException(String message){
        super(message,ERROR_CODE.RESOURCE_ALREADY_EXISTS);
    }

}
