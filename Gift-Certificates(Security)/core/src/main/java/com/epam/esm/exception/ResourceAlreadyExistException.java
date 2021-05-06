package com.epam.esm.exception;

import lombok.Getter;

/**
 * ResourceAlreadyExistException is used when required resource has already existed.
 */
@Getter
public class ResourceAlreadyExistException extends Exception {

    private String code;
    private String nameOfResource;

    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(String code, String nameOfResource) {
        this.code = code;
        this.nameOfResource = nameOfResource;
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
