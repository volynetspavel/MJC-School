package com.epam.esm.exception;

import lombok.Getter;

/**
 * ValidationException is used when some parameters are not valid.
 */
@Getter
public class ValidationException extends Exception {

    private final String code = "99";

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
