package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class-wrapper for authentication user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthenticationRequestDto extends AbstractDto<Integer> {

    @NotNull
    @Email(regexp = "[A-Za-z0-9+_.-]+@(.+)")
    private String email;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{4,256}")
    private String password;
}
