package com.compass.challenge3.exceptions;

import lombok.Getter;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class Failed extends RuntimeException{
    private final String resourceName;
    private final String fieldName;
    private final long fieldValue;

    public Failed(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s with %s : '%s' not found", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}