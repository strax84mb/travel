package com.htec.travel.users;

import com.htec.travel.security.JwtTokenProvider;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDto createUser(CreateUserDto input) throws NoSuchAlgorithmException {
        var salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        var pass = encryptPassword(salt, input.getPassword());
        var user = createNewEntity(input, salt, pass, UserRole.USER);
        return convertToDto(userRepository.save(user));
    }

    @Override
    public UserDto createAdmin(CreateUserDto input) throws NoSuchAlgorithmException {
        var salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        var pass = encryptPassword(salt, input.getPassword());
        var user = createNewEntity(input, salt, pass, UserRole.ADMIN);
        return convertToDto(userRepository.save(user));
    }

    private String encryptPassword(byte[] salt, String password) throws NoSuchAlgorithmException {
        var digester = MessageDigest.getInstance("SHA-256");
        digester.update(salt);
        var pass = digester.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(pass);
    }

    private User createNewEntity(CreateUserDto input, byte[] salt, String password, UserRole userRole) {
        return User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .salt(salt)
                .password(password)
                .role(userRole)
                .username(input.getUsername())
                .build();
    }

    @Override
    public LoginResponseDto login(LoginUserDto input) throws NoSuchAlgorithmException {
        var user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new WrongCredentialsException());
        var encryptedPassword = encryptPassword(user.getSalt(), input.getPassword());
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new WrongCredentialsException();
        }
        /*authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), encryptedPassword));
        var token = jwtTokenProvider.createToken(input.getUsername(), List.of(user.getRole().name()));*/
        var token = jwtTokenProvider.createJwtToken(user.getUsername(), user.getPassword(), List.of(user.getRole().name()));
        return LoginResponseDto.builder()
                .user(convertToDto(user))
                .token(token)
                .build();
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}
