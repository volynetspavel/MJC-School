package com.epam.esm.exception;

import com.epam.esm.exception.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handle(ResourceNotFoundException ex) {
        Error error = new Error(Integer.valueOf(ex.getCode()), ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Error> handle(ResourceAlreadyExistException ex) {
        Error error = new Error(Integer.valueOf(ex.getCode()), ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
}
