package com.epam.esm.hateoas;

import com.epam.esm.controller.PurchaseController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object UserDto.
 */
@Component
public class UserHateoas {

    private static final String PURCHASES = "purchases";

    public void addLinksForUserDto(UserDto userDto) throws ResourceNotFoundException {

        Link selfLink = linkTo(UserController.class)
                .slash(userDto.getId())
                .withSelfRel();

        Map<String, String> params = new HashMap<>();

        Link purchases = linkTo(methodOn(PurchaseController.class)
                .findPurchasesByUserId(userDto.getId(), params))
                .withRel(PURCHASES);

        userDto.add(selfLink);
        userDto.add(purchases);
    }

    public CollectionModel<UserDto> addLinksForListOfUserDto(List<UserDto> users) throws ResourceNotFoundException {

        for (UserDto user : users) {
            addLinksForUserDto(user);
        }

        Link selfLink = linkTo(UserController.class)
                .withSelfRel();
        return CollectionModel.of(users, selfLink);
    }
}
