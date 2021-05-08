package com.epam.esm.service.impl;

import com.epam.esm.constant.CodeException;
import com.epam.esm.dao.UserDao;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * Service which loads user-specific data.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(CodeException.RESOURCE_NOT_FOUND);
        }
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
    }
}
