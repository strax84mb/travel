package com.htec.travel.cities;

import java.math.BigInteger;

public interface CommentService {

    CommentDto comment(BigInteger cityId, String description, String username);

    CommentDto updateComment(BigInteger id, String description, String username);

    boolean deleteComment(BigInteger id, String username);

    void adminDeleteComment(BigInteger id);
}
