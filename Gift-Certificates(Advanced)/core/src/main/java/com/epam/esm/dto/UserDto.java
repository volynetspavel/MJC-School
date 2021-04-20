package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class-wrapper for user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto<Integer> {

    private String name;
    private String surname;
    private String email;
}
