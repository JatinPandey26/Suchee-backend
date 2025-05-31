package com.suchee.app.exception;

import com.cloudinary.api.exceptions.ApiException;

public class AsyncHandlerNotFoundException extends ApiException {

    public AsyncHandlerNotFoundException(String eventType) {
        super("No AsyncHandler registered for event type: " + eventType);
    }
}
