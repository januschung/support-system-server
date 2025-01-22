package com.supportsystem.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName,  Object fieldValue) {
        super(String.format("Could not find %s with %s: %s",  resourceName, fieldName, fieldValue));
    }
}
