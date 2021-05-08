package com.epam.esm.service;

import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.model.User;
import com.epam.esm.service.crud.ReadableService;

/**
 * This class is a layer for interacting with UserDao.
 */
public abstract class UserService implements ReadableService<UserDto, User> {

    public abstract UserDto registration(RegistrationUserDto newUserDto) throws ResourceAlreadyExistException;

}
