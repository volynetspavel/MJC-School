package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.hateoas.UserHateoas;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * Class is used to send requests from the client to the service layer for user entity.
 */
@RestController
@RequestMapping("/users")
@Validated
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
    public CollectionModel<UserDto> findAll(@RequestParam Map<String, String> params)
            throws ResourceNotFoundException, ValidationParametersException {
        List<UserDto> userList = userService.findAll(params);
        return userHateoas.addLinksForListOfUserDto(userList);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id")
                            @Min(value = 1) int id)
            throws ResourceNotFoundException, ValidationParametersException {
        UserDto userDto = userService.findById(id);
        userHateoas.addLinksForUserDto(userDto);
        return userDto;
    }
}
