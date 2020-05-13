package com.htec.travel.controllers;

import com.htec.travel.cities.City;
import com.htec.travel.cities.CityRepository;
import com.htec.travel.cities.CommentService;
import com.htec.travel.users.CreateUserDto;
import com.htec.travel.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializeRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CommentService commentService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.createUser(CreateUserDto.builder()
                .firstName("Strahinja")
                .lastName("Dobrijevic")
                .username("strahinjamalabosna")
                .password("strale84")
                .build());
        userService.createAdmin(CreateUserDto.builder()
                .firstName("admin")
                .lastName("admin")
                .username("administrator")
                .password("administrator84")
                .build());
        var city = cityRepository.save(City.builder()
                .name("Novi Sad")
                .country("Serbia")
                .description("Nice town")
                .build());
        var comment = commentService.comment(city.getId(), "qwerty", "strahinjamalabosna");
        log.info("Comment ID >>> " + comment.getId());
    }
}
