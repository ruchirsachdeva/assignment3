package com.lnu.foundation.auth;

import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public final class TokenHandler {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    private final String secret;

    private final UserRepository userRepository;

    @Autowired
    public TokenHandler(@Value("${app.jwt.secret}") String secret,
                        UserRepository userRepository) {
        this.secret = secret;
        this.userRepository = userRepository;
    }

    public Optional<User> parseUserFromToken(String token) {
        final String subject = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userRepository.findByUsername(subject);
    }

    public String createTokenForUser(User user) {
        final ZonedDateTime afterOneWeek = ZonedDateTime.now().plusWeeks(1);

        return Jwts.builder()
                .setSubject(user.getUsername().toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(Date.from(afterOneWeek.toInstant()))
                .compact();
    }
}

