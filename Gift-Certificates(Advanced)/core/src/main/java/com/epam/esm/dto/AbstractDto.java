package com.epam.esm.dto;

import lombok.Data;

/**
 * Abstract class for data transfer object classes.
 */
@Data
public class AbstractDto<T extends Number> {

    private T id;
}
