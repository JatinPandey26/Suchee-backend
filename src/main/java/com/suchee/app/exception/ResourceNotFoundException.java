package com.suchee.app.exception;

public class ResourceNotFoundException extends AppException{

    public ResourceNotFoundException(String resourceName, Long id){
        super("Resource : "  + resourceName + " not found with id " + id,"RESOURCE_NOT_FOUND");
    }

}
