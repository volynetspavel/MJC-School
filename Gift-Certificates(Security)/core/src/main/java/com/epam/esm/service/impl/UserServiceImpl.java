package com.epam.esm.service.impl;

import com.epam.esm.constant.CodeException;
import com.epam.esm.constant.RoleValue;
import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.security.SecurityUtil;
import com.epam.esm.service.UserService;
import com.epam.esm.validation.PaginationValidator;
import com.epam.esm.validation.SecurityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is an implementation of UserService.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private UserMapper userMapper;
    private PaginationValidator paginationValidator;
    private PasswordEncoder passwordEncoder;

    private int offset;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserMapper userMapper,
                           PaginationValidator paginationValidator,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userMapper = userMapper;
        this.paginationValidator = paginationValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll(Map<String, String> params) throws ValidationParametersException {
        int limit = (int) userDao.count();
        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        List<User> users = userDao.findAll(PageRequest.of(offset, limit)).toList();
        return migrateListFromEntityToDto(users);
    }

    @Override
    public UserDto findById(int id) throws ResourceNotFoundException {
        if (SecurityValidator.isCurrentUserHasRoleUser()) {
            id = Objects.requireNonNull(SecurityUtil.getJwtUserId());
        }
        Optional<User> user = userDao.findById(id);
        checkEntityOnNull(user, id);
        return userMapper.toDto(user.get());
    }

    private List<UserDto> migrateListFromEntityToDto(List<User> users) {
        return users.stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto registration(RegistrationUserDto newUserDto) throws ResourceAlreadyExistException {
        String userEmail = newUserDto.getEmail();
        Optional<User> user = userDao.findByEmail(userEmail);
        if (user.isPresent()) {
            throw new ResourceAlreadyExistException(CodeException.RESOURCE_ALREADY_EXIST, userEmail);
        }
        Optional<Role> role = roleDao.findByName(RoleValue.ROLE_USER);

        User newUser = new User();
        newUser.setName(newUserDto.getName());
        newUser.setSurname(newUserDto.getSurname());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        newUser.setRoles(Collections.singletonList(role.get()));

        userDao.save(newUser);
        return userMapper.toDto(newUser);
    }
}
