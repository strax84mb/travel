package com.htec.travel.security;

import com.htec.travel.users.UserRepository;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Aspect
@Component
public class HasAnyRoleAspect {

    byte[] signingKey = "mySecret".getBytes(StandardCharsets.UTF_8);

    @Autowired
    private UserRepository userRepository;

    @Around("@annotation(HasAnyRole)")
    public Object hasAnyRole(ProceedingJoinPoint pjp) throws Throwable {
        var method = ((MethodSignature) pjp.getSignature()).getMethod();
        String[] allowedRoles = method.getAnnotation(HasAnyRole.class).value();
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var token = request.getHeader("Authorization").substring("Bearer ".length());
        var claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token);
        if (claims.getBody().getExpiration().before(new Date())) {
            throw new UnauthorizedException();
        }
        var username = claims.getBody().getSubject();
        var md5Password = claims.getBody().get("password", String.class);
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException());
        var encodedOriginalPassword = MD5HashingUtil.md5(user.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!encodedOriginalPassword.equals(md5Password)) {
            throw new UnauthorizedException();
        }
        if (ArrayUtils.contains(allowedRoles, user.getRole().name())) {
            enrichWithUserData(pjp.getArgs(), user.getId(), user.getUsername());
            return pjp.proceed();
        }
        throw new UnauthorizedException();
    }

    private void enrichWithUserData(Object[] args, BigInteger id, String username) {
        for (Object arg : args) {
            if (arg instanceof JwtUser) {
                var jwtUser = (JwtUser) arg;
                jwtUser.setUserId(id);
                jwtUser.setUsername(username);
            }
        }
    }
}
