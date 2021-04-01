package com.epam.esm.exception;

import lombok.Getter;

/**
 * ResourceNotFoundException is used when required resource is not found.
 */
@Getter
public class ResourceNotFoundException extends Exception {
    private final String code = "7701";

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
