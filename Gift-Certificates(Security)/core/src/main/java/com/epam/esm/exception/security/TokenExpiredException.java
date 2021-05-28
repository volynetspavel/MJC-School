package com.epam.esm.exception.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * TokenExpiredException is used when token is expired.
 */
@Getter
public class TokenExpiredException extends AuthenticationException {

    public TokenExpiredException(String code) {
        super(code);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpiredException(Throwable cause) {
        super(String.valueOf(cause));
    }
}
