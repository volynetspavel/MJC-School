package com.epam.esm.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class used for print any errors or exceptions on backend side.
 */
@Getter
@AllArgsConstructor
public class ExceptionMessage {

    private int code;
    private String message;

}
