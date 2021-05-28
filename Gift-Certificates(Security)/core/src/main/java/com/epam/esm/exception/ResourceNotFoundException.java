package com.epam.esm.exception;

import lombok.Getter;

/**
 * ResourceNotFoundException is used when required resource is not found.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private int entityId;

    public ResourceNotFoundException(String code) {
        super(code);
    }

    public ResourceNotFoundException(String code, int entityId) {
        super(code);
        this.entityId = entityId;
    }

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
