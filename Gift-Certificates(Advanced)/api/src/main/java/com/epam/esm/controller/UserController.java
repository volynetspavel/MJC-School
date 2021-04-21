package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.UserHateoas;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class is used to send requests from the client to the service layer for user entity.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserHateoas userHateoas;

    @Autowired
    public UserController(UserService userService, UserHateoas userHateoas) {
        this.userService = userService;
        this.userHateoas = userHateoas;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<UserDto> findAll(@RequestParam Map<String, String> params) throws ResourceNotFoundException {
        List<UserDto> userList = userService.findAll(params);
        return userHateoas.addLinksForListOfUserDto(userList);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") int id) throws ResourceNotFoundException {
        UserDto userDto = userService.findById(id);
        userHateoas.addLinksForUserDto(userDto);
        return userDto;
    }
}
