package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is an implementation of UserService.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private UserDao userDao;
    private UserMapper userMapper;

    private int limit;
    private int offset = 0;

    public UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll(Map<String, String> params) throws ResourceNotFoundException {
        limit = userDao.getCount();

        if (params.containsKey(SIZE) && params.containsKey(PAGE)) {
            limit = Integer.parseInt(params.get(SIZE));
            offset = (Integer.parseInt(params.get(PAGE)) - 1) * limit;
        }
        List<User> users = userDao.findAll(offset, limit);
        List<UserDto> userList = migrateListFromEntityToDto(users);
        checkListOnEmptyOrNull(userList);

        return userList;
    }

    @Override
    public UserDto findById(int id) throws ResourceNotFoundException {
        User user = userDao.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("Requested resource not found (id = " + id + ")");
        }
        return userMapper.toDto(user);
    }

    private List<UserDto> migrateListFromEntityToDto(List<User> users) {
        return users.stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }
}
