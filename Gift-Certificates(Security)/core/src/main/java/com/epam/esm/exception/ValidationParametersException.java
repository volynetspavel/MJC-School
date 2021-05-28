package com.epam.esm.exception;

import lombok.Getter;

/**
 * ValidationException is used when some parameters are not valid.
 */
@Getter
public class ValidationParametersException extends RuntimeException {

    public ValidationParametersException() {
        super();
    }

    public ValidationParametersException(String code) {
        super(code);
    }

    public ValidationParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationParametersException(Throwable cause) {
        super(cause);
    }
}
