package com.epam.esm.exception;

import com.epam.esm.constant.CodeException;
import com.epam.esm.exception.model.ExceptionMessage;
import com.epam.esm.exception.model.ListErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for handling exceptions in specific handler classes and/or handler methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handle(ResourceNotFoundException ex, HttpServletRequest request) {
        String code = ex.getCode();
        int entityId = ex.getEntityId();
        String exMessage = messageSource.getMessage(code, new Object[]{entityId}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionMessage> handle(ResourceAlreadyExistException ex, HttpServletRequest request) {
        String code = ex.getCode();
        String nameOfResource = ex.getNameOfResource();
        String exMessage = messageSource.getMessage(code, new Object[]{nameOfResource}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionMessage> handle(ServiceException ex, HttpServletRequest request) {
        String code = ex.getCode();
        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationParametersException.class)
    public ResponseEntity<ExceptionMessage> handle(ValidationParametersException ex, HttpServletRequest request) {
        String code = ex.getCode();
        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionMessage> handleAuthenticationException(AuthenticationException ex,
                                                                          HttpServletRequest request) {
        String code = ex.getMessage();
        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUsernameNotFoundException(UsernameNotFoundException ex,
                                                                            HttpServletRequest request) {
        String code = ex.getMessage();
        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    /**
     * Catch exception when fields of dto-entities are not valid.
     *
     * @param ex      - MethodArgumentNotValidException
     * @param request - HttpServletRequest
     * @return - exception message with code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ListErrorMessage> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String code = CodeException.INVALID_FIELD;

        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, request.getLocale()))
                .collect(Collectors.toList());

        ListErrorMessage listErrorMessage = new ListErrorMessage(Integer.parseInt(code), errorMessages);
        return new ResponseEntity<>(listErrorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch exception when uri contains incorrect data, which check in controllers.
     *
     * @param ex      - ConstraintViolationException
     * @param request - HttpServletRequest
     * @return - exception message with code.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> handle(ConstraintViolationException ex, HttpServletRequest request) {
        String code = CodeException.INVALID_DATA;
        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());

        ExceptionMessage exceptionMessage = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch exception when uri contains incorrect type of data.
     * For example, tag/7 - find tag by id, expected int, but enter string - tag/ddf.
     *
     * @param e  - MethodArgumentTypeMismatchException
     * @param wr - WebRequest
     * @return - exception message with code.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessage> handle(MethodArgumentTypeMismatchException e, WebRequest wr) {
        String code = CodeException.INVALID_URI;

        String errorMessage = messageSource.getMessage(code, new Object[]{}, wr.getLocale());
        ExceptionMessage response = new ExceptionMessage(Integer.parseInt(code), errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch exception when parameters of request are missed.
     * For example, when execute method findCertificatesBySeveralTags from {@see CertificateController}
     *
     * @param e  - MissingServletRequestParameterException.
     * @param wr - web request.
     * @return - exception message with code.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionMessage> handle(MissingServletRequestParameterException e, WebRequest wr) {
        String code = CodeException.NOT_VALID_PARAMETER_OF_REQUEST;

        String errorMessage = messageSource.getMessage(code, new Object[]{}, wr.getLocale());
        ExceptionMessage response = new ExceptionMessage(Integer.parseInt(code), errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch exception when trying to insert entity which were removed,
     * and audit-table consist information about that.
     *
     * @param e       - JpaSystemException
     * @param request - HttpServletRequest
     * @return - exception message with code.
     */
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ExceptionMessage> handle(JpaSystemException e, HttpServletRequest request) {
        e.printStackTrace();
        String code = CodeException.AUDIT_LOG_ERROR;

        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());
        ExceptionMessage response = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionMessage> handleThrowable(Throwable e, HttpServletRequest request) {
        e.printStackTrace();
        String code = CodeException.UNEXPECTED_ERROR;

        String exMessage = messageSource.getMessage(code, new Object[]{}, request.getLocale());
        ExceptionMessage response = new ExceptionMessage(Integer.parseInt(code), exMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
