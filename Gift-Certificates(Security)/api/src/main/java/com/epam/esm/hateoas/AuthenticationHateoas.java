package com.epam.esm.hateoas;

import com.epam.esm.controller.PurchaseController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtUser;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object AuthenticationResponseDto.
 */
@Component
public class AuthenticationHateoas {

    private static final String PURCHASES = "purchases";

    public void addLinksForUserAfterLogin(JwtUser jwtUser, AuthenticationResponseDto userDto) {
        int id = jwtUser.getId();
        Link selfLink = linkTo(UserController.class)
                .slash(id)
                .withSelfRel();

        Map<String, String> params = new HashMap<>();
        Link purchases = linkTo(methodOn(PurchaseController.class)
                .findPurchasesByUserId(id, params))
                .withRel(PURCHASES);

        userDto.add(selfLink);
        userDto.add(purchases);
    }

    public void addLinksForUserAfterRegistration(UserDto userDto, AuthenticationResponseDto authenticationResponseDto) {
        Link selfLink = linkTo(UserController.class)
                .slash(userDto.getId())
                .withSelfRel();
        authenticationResponseDto.add(selfLink);
    }
}
