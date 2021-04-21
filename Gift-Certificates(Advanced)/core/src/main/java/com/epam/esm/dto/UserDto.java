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

    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "[A-Za-zА-Я \\-]+")
    private String name;
    @NotBlank(message = "Surname must not be blank")
    @Pattern(regexp = "[A-Za-zА-Я \\-]+")
    private String surname;
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$]", message = "Please, enter correct email.")
    private String email;
}
