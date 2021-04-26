package com.epam.esm.exception;

import lombok.Getter;

/**
 * ValidationException is used when some parameters are not valid.
 */
@Getter
public class ValidationParametersException extends Exception {

    private String code;

    public ValidationParametersException() {
        super();
    }

    public ValidationParametersException(String code) {
        this.code = code;
    }

    public ValidationParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationParametersException(Throwable cause) {
        super(cause);
    }
}
