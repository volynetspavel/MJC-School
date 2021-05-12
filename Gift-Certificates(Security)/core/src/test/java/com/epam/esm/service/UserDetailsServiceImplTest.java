package com.epam.esm.service;

import com.epam.esm.constant.RoleValue;
import com.epam.esm.dao.UserDao;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.security.JwtUser;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Class for testing methods from service layer for user.
 */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserDao userDao;

    @DisplayName("Testing method loadUserByUsername() on positive result")
    @Test
    void loadUserByUsernameSuccessTest() {
        int id = 1;
        String name = "Jonh";
        String surname = "Smith";
        String email = "jonhy@mail.com";
        String password = "$2y$12$y7VYtQv9l2eMgasIDeGoRerE/sIS09CfzSLIgQL1D1n3P2AcbD2Hu";
        Role role = new Role();
        role.setName(RoleValue.ROLE_USER);
        List<Role> roles = Collections.singletonList(role);

        User expected = createUser(id, name, surname, email, password, roles);
        UserDetails expectedUserDetails = createJwtUser(id, email, password, roles);

        when(userDao.findByEmail(email)).thenReturn(expected);
        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(email);

        Assertions.assertEquals(expectedUserDetails, actualUserDetails);
    }

    @DisplayName("Testing method loadUserByUsername() on exception")
    @Test
    void loadUserByUsernameThrowsExceptionTest() throws UsernameNotFoundException {

        String email = "jonhy@mail.com";
        User expected = null;

        when(userDao.findByEmail(email)).thenReturn(expected);

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));
    }

    private JwtUser createJwtUser(int id, String email, String password, List<Role> roles) {
        return new JwtUser(id, email, password, roles);
    }

    private User createUser(int id, String name, String surname, String email, String password, List<Role> roles) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);

        return user;
    }
}
