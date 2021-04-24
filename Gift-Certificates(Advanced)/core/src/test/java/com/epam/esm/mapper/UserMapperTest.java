package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.when;

/**
 * Class for testing user mapper.
 */
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    @Mock
    private ModelMapper mapper;

    @DisplayName("Testing method toEntity() on positive result")
    @Test
    void toEntitySuccessTest() {
        User expectedUser = getUser();
        UserDto userDto = getUserDto();

        when(mapper.map(userDto, User.class)).thenReturn(expectedUser);
        when(userMapper.toEntity(userDto)).thenReturn(expectedUser);

        User actualUser = userMapper.toEntity(userDto);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @DisplayName("Testing method toEntity() on negative result, when user is null")
    @Test
    void toEntityNegativeTest() {
        UserDto userDto = null;

        User actualUser = userMapper.toEntity(userDto);
        Assertions.assertNull(actualUser);
    }

    @DisplayName("Testing method toDto() on positive result")
    @Test
    void toDtoSuccessTest() {
        User user = getUser();
        UserDto expectedUserDto = getUserDto();

        when(mapper.map(user, UserDto.class)).thenReturn(expectedUserDto);
        when(userMapper.toDto(user)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userMapper.toDto(user);
        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    @DisplayName("Testing method toDto() on negative result, when UserDto is null")
    @Test
    void toDtoNegativeTest() {
        User user = null;

        UserDto actualUserDto = userMapper.toDto(user);
        Assertions.assertNull(actualUserDto);
    }

    private User getUser() {
        int id = 1;
        String name = "Jonh";
        String surname = "Smith";
        String email = "jonhy@mail.com";
        return createUser(id, name, surname, email);
    }

    private UserDto getUserDto() {
        int id = 1;
        String name = "Jonh";
        String surname = "Smith";
        String email = "jonhy@mail.com";
        return createUserDto(id, name, surname, email);
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
