package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class-wrapper for registration user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegistrationUserDto extends AbstractDto<Integer> {

    @NotBlank
    @Pattern(regexp = "[A-Za-z \\-]+")
    private String name;

    @NotBlank
    @Pattern(regexp = "[A-Za-z \\-]+")
    private String surname;

    @NotNull
    @Email(regexp = "[A-Za-z0-9+_.-]+@(.+)")
    private String email;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{4,}")
    private String password;

}