package com.epam.esm.exception;

import lombok.Getter;

/**
 * ResourceAlreadyExistException is used when required resource has already existed.
 */
@Getter
public class ResourceAlreadyExistException extends RuntimeException {

    private String nameOfResource;

    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException(String code, String nameOfResource) {
        super(code);
        this.nameOfResource = nameOfResource;
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
