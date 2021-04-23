package com.epam.esm.exception;

import com.epam.esm.exception.model.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.Email;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final int METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE = 2221;

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

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionMessage> handle(ServiceException ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(ex.getCode()), ex.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionMessage> handle(ValidationException ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(ex.getCode()), ex.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handle(MethodArgumentNotValidException ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE,
                ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}
