package com.epam.esm.exception.model;

/**
 * Class used for print any errors or exceptions on backend side.
 */
public class ExceptionMessage {
    private int code;
    private String message;

    public ExceptionMessage() {
    }

    public ExceptionMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
