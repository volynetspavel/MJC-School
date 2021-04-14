package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

/**
 * This class is a layer for interacting with UserDao.
 */
public interface UserService extends Service<UserDto> {

    @Override
    default UserDto insert(UserDto entityDto) {
        return null;
    }

    @Override
    default void delete(int id) {
    }

    @Override
    default UserDto update(UserDto entityDto) {
        return null;
    }
}
