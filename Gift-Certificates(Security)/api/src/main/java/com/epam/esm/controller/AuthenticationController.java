package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.hateoas.AuthenticationHateoas;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.security.JwtUser;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Class is used for security operations: registration and login.
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationHateoas authenticationHateoas;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider, AuthenticationHateoas authenticationHateoas) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationHateoas = authenticationHateoas;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        String token = jwtTokenProvider.createToken(request.getEmail());
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(request.getEmail(), token);
        authenticationHateoas.addLinksForUserAfterLogin(jwtUser, authenticationResponseDto);
        return authenticationResponseDto;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public AuthenticationResponseDto registration(@RequestBody @Valid RegistrationUserDto newUserDto)
            throws ResourceAlreadyExistException {
        UserDto userDto = userService.registration(newUserDto);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUserDto.getEmail(),
                newUserDto.getPassword()));
        String token = jwtTokenProvider.createToken(newUserDto.getEmail());

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(newUserDto.getEmail(), token);
        authenticationHateoas.addLinksForUserAfterRegistration(userDto, authenticationResponseDto);
        return authenticationResponseDto;
    }
}
