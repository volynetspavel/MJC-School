package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.model.User;
import com.epam.esm.service.crud.ReadableService;

/**
 * This class is a layer for interacting with UserDao.
 */
public abstract class UserService implements ReadableService<UserDto, User> {

}
