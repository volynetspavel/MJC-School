package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class-wrapper for tag.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TagDto extends AbstractDto<Integer> {

    private String name;
}
