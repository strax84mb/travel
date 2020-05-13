package com.htec.travel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private long validityInMilliseconds = 3600000; // 1h

    private String base64EncodedSecretKey = Base64.getEncoder().encodeToString("mySecret".getBytes(StandardCharsets.UTF_8));

    public String createJwtToken(String username, String password, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        var md5Password = MD5HashingUtil.md5(password.getBytes(StandardCharsets.UTF_8));
        var map = Map.of(
                "password", md5Password,
                "roles", roles
        );
        claims.putAll(map);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }
}
