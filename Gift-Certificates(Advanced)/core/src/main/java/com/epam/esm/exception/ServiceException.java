package com.epam.esm.exception;

import lombok.Getter;

/**
 * General exception for any errors in service layer.
 */
@Getter
public class ServiceException extends Exception {

    private String code;

    public ServiceException() {
        super();
    }

    public ServiceException(String code) {
        this.code = code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
