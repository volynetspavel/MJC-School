package com.epam.esm.exception;

import com.epam.esm.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(ResourceNotFoundException ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(ex.getCode()), ex.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionMessage> handle(ResourceAlreadyExistException ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(ex.getCode()), ex.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }
}
