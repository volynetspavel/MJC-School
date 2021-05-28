package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Class-wrapper for user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto extends AbstractDto<Integer> {

    @NotBlank
    @Pattern(regexp = "[A-Za-z \\-]+")
    private String name;

    @NotBlank
    @Pattern(regexp = "[A-Za-z \\-]+")
    private String surname;

    @Email(regexp = "[A-Za-z0-9+_.-]+@(.+)")
    private String email;
}