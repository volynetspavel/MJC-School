package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import com.epam.esm.validation.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is an implementation of UserService.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserMapper userMapper;
    private PaginationValidator paginationValidator;

    private int offset;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper, PaginationValidator paginationValidator) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.paginationValidator = paginationValidator;
    }

    @Override
    public List<UserDto> findAll(Map<String, String> params) throws ValidationParametersException {
        int limit = userDao.getCount();
        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        List<User> users = userDao.findAll(offset, limit);
        return migrateListFromEntityToDto(users);
    }

    @Override
    public UserDto findById(int id) throws ResourceNotFoundException {
        User user = userDao.findById(id);
        checkEntityOnNull(user, id);
        return userMapper.toDto(user);
    }

    private List<UserDto> migrateListFromEntityToDto(List<User> users) {
        return users.stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }
}
