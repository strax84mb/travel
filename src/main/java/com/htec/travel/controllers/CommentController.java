package com.htec.travel.controllers;

import com.htec.travel.cities.CommentDescriptionDto;
import com.htec.travel.cities.CommentDto;
import com.htec.travel.cities.CommentService;
import com.htec.travel.security.HasAnyRole;
import com.htec.travel.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "city")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @HasAnyRole({"USER"})
    @PostMapping(path = "/{cityId}/comment", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommentDto comment(@RequestBody @Valid CommentDescriptionDto input,
                              @PathVariable("cityId") BigInteger cityId,
                              JwtUser jwtUser) {
        return commentService.comment(cityId, input.getText(), jwtUser.getUsername());
    }

    @HasAnyRole({"USER"})
    @PutMapping(path = "/comment/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommentDto updateComment(@PathVariable("id") BigInteger id,
                                    @RequestBody @Valid CommentDescriptionDto input,
                                    JwtUser jwtUser) {
        return commentService.updateComment(id, input.getText(), jwtUser.getUsername());
    }

    @HasAnyRole({"USER"})
    @DeleteMapping(path = "/comment/{id}")
    public boolean deleteComment(@PathVariable("id") BigInteger id, JwtUser jwtUser) {
        return commentService.deleteComment(id, jwtUser.getUsername());
    }

    @HasAnyRole({"ADMIN"})
    @DeleteMapping(path = "/comment/{id}/admin")
    public String adminDeleteComment(@PathVariable("id") BigInteger id) {
        commentService.adminDeleteComment(id);
        return "OK";
    }
}
