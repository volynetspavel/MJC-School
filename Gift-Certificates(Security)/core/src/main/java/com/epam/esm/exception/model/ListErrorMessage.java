package com.epam.esm.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Class used for print list of errors or exceptions on backend side.
 */
@Getter
@AllArgsConstructor
public class ListErrorMessage {

    private int code;
    private List<String> messages;

}
