package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

/**
 * Class-wrapper for response after authentication user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AuthenticationResponseDto extends RepresentationModel<AuthenticationResponseDto> {

    private String email;
    private String token;
}
