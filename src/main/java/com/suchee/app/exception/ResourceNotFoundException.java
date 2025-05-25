package com.suchee.app.exception;

public class ResourceNotFoundException extends AppException{

    public ResourceNotFoundException(String resourceName, Long id){
        super("Resource : "  + resourceName + " not found with id " + id,ERROR_CODE.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String resourceName, Object Column , Object identifier){
        super("Resource : "  + resourceName + " not found with " + Column + " : " + identifier,ERROR_CODE.RESOURCE_NOT_FOUND);
    }
}
