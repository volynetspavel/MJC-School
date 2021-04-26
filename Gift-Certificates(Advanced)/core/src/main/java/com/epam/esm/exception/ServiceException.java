package com.epam.esm.exception;

import lombok.Getter;

/**
 * General exception for any errors in service layer.
 */
@Getter
public class ServiceException extends Exception {

    private final String code = "1001";

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
