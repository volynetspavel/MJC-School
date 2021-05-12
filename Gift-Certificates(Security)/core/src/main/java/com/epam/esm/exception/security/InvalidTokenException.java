package com.epam.esm.exception.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * InvalidTokenException is used when token is not valid.
 */
@Getter
public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String code) {
        super(code);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(String.valueOf(cause));
    }
}
