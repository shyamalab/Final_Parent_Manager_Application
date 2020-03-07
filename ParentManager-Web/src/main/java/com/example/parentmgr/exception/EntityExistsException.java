package com.example.parentmgr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityExistsException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public EntityExistsException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exist with same %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
