package com.htec.travel.cities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CommentRepository extends JpaRepository<Comment, BigInteger> {

    long deleteByIdAndPosterUsername(BigInteger id, String username);
}
