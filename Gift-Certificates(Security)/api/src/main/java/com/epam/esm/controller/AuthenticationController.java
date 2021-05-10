package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public AuthenticationResponseDto authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtTokenProvider.createToken(request.getEmail());
        return new AuthenticationResponseDto(request.getEmail(), token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public AuthenticationResponseDto registration(@RequestBody @Valid RegistrationUserDto newUserDto)
            throws ResourceAlreadyExistException {
                userService.registration(newUserDto);
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setEmail(newUserDto.getEmail());
        authenticationRequestDto.setPassword(newUserDto.getPassword());
        return authenticate(authenticationRequestDto);
    }
}
