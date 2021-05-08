package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class-wrapper for response after authentication user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AuthenticationResponseDto {

    private String email;
    private String token;
}
