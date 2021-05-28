package com.epam.esm.mapper.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.AbstractMapper;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for user entity.
 */
@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    public UserMapper() {
        super(User.class, UserDto.class);
    }
}
