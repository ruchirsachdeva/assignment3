package com.lnu.foundation.service;

import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rucsac on 20/10/2018.
 */

@Service
public class SecurityContextService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    public Authentication authenticate(Authentication loginToken) {
        Authentication authenticate = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return authenticate;
    }
}
