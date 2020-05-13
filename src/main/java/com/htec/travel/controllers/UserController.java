package com.htec.travel.controllers;

import com.htec.travel.users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserDto createAdmin() {
        return null;
    }

    @PostMapping(path = "/signup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserDto signup(@RequestBody @Valid CreateUserDto dto) throws NoSuchAlgorithmException {
        return userService.createUser(dto);
    }

    @PostMapping(path = "/login", consumes = APPLICATION_JSON_VALUE)
    public LoginResponseDto login(@RequestBody @Valid LoginUserDto dto) throws NoSuchAlgorithmException {
        return userService.login(dto);
    }
}
