package com.epam.esm.hateoas;

import com.epam.esm.controller.PurchaseController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for assemble links for object UserDto.
 */
@Component
public class UserHateoas {

    private static final String PURCHASES = "purchases";

    public void addLinksForUserDto(UserDto userDto) {

        Link selfLink = linkTo(UserController.class)
                .slash(userDto.getId())
                .withSelfRel();

        Link purchases = linkTo(methodOn(PurchaseController.class)
                .findPurchasesByUserId(userDto.getId()))
                .withRel(PURCHASES);

        userDto.add(selfLink);
        userDto.add(purchases);
    }

    public CollectionModel<UserDto> addLinksForListOfUserDto(List<UserDto> users) {

        for (UserDto user : users) {
            addLinksForUserDto(user);
        }

        Link selfLink = linkTo(UserController.class)
                .withSelfRel();
        return CollectionModel.of(users, selfLink);
    }
}
