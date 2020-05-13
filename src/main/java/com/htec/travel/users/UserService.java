package com.htec.travel.users;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    UserDto createUser(CreateUserDto input) throws NoSuchAlgorithmException;

    UserDto createAdmin(CreateUserDto input) throws NoSuchAlgorithmException;

    LoginResponseDto login(LoginUserDto input) throws NoSuchAlgorithmException;
}
