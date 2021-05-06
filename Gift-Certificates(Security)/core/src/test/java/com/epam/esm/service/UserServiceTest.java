package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.validation.PaginationValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Class for testing methods from service layer for user.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PaginationValidator paginationValidator;

    @DisplayName("Testing method findById() on positive result")
    @Test
    void findByIdSuccessTest() throws ResourceNotFoundException {
        int id = 1;
        String name = "Jonh";
        String surname = "Smith";
        String email = "jonhy@mail.com";

        User expected = createUser(id, name, surname, email);
        UserDto expectedDto = createUserDto(id, name, surname, email);

        when(userDao.findById(id)).thenReturn(expected);
        when(userMapper.toDto(expected)).thenReturn(expectedDto);
        UserDto actualDto = userService.findById(id);

        assertEquals(expectedDto, actualDto);
    }

    @DisplayName("Testing method findById() on exception")
    @Test
    void findByIdThrowsExceptionTest() {
        int id = 1;
        when(userDao.findById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(id));
    }

    @DisplayName("Testing method findAll() on positive result")
    @Test
    void findAllSuccessTest() throws ValidationParametersException {

        int id1 = 1;
        String name1 = "Jonh";
        String surname1 = "Smith";
        String email1 = "jonhy@mail.com";
        User user1 = createUser(id1, name1, surname1, email1);

        int id2 = 2;
        String name2 = "Max";
        String surname2 = "Smith";
        String email2 = "max@mail.com";
        User user2 = createUser(id2, name2, surname2, email2);

        int id3 = 3;
        String name3 = "Julia";
        String surname3 = "Smith";
        String email3 = "julia@mail.com";
        User user3 = createUser(id3, name3, surname3, email3);

        List<User> expectedUserList = Arrays.asList(user1, user2, user3);

        UserDto userDto1 = createUserDto(id1, name1, surname1, email1);
        UserDto userDto2 = createUserDto(id2, name2, surname2, email2);
        UserDto userDto3 = createUserDto(id3, name3, surname3, email3);

        List<UserDto> expectedUserDtoList = Arrays.asList(userDto1, userDto2, userDto3);

        int offset = 0;
        int limit = 3;
        when(userDao.getCount()).thenReturn(3);
        when(paginationValidator.validatePaginationParameters(new HashMap<>())).thenReturn(false);

        when(userDao.findAll(offset, limit)).thenReturn(expectedUserList);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);
        when(userMapper.toDto(user3)).thenReturn(userDto3);

        List<UserDto> actualUserDtoList = userService.findAll(new HashMap<>());

        assertEquals(expectedUserDtoList, actualUserDtoList);
    }

    private User createUser(int id, String name, String surname, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);

        return user;
    }

    private UserDto createUserDto(int id, String name, String surname, String email) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setSurname(surname);
        userDto.setEmail(email);

        return userDto;
    }
}
