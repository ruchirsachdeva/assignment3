package com.lnu.foundation.controller;

import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.service.SecurityContextService;
import com.lnu.foundation.service.SocialUserService;
import com.lnu.foundation.service.UserParamsValidator;
import com.lnu.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    UserService service;

    @Autowired
    private UserParamsValidator signupFormValidator;

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SocialUserService socialUserService;

    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/me/tests")
    public Collection<TestSession> getTestSessions() {
        User user = securityContextService.currentUser().orElseThrow(RuntimeException::new);
        Collection<TestSession> sessions = service.getSessions(user.getUsername());
        return sessions.stream()
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("user/{username}/tests")
    public Collection<TestSession> getPatientTestSessions(@PathVariable String username) {

        Collection<TestSession> sessions = service.getPatientSessions(username);
        return sessions.stream()
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/me")
    public User getMe() {
        return securityContextService.currentUser().orElseThrow(RuntimeException::new);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/therapies")
    public Collection<Therapy> getTherapies() {
        Collection<Therapy> therapies = null;
        User user = securityContextService.currentUser().orElseThrow(RuntimeException::new);
        if ("physician".equals(user.getRole().getName())
                || "researcher".equals(user.getRole().getName())
                || "junior researcher".equals(user.getRole().getName())) {
            therapies = service.getTherapiesByMed(user.getUsername());
        } else {
            therapies = service.getTherapiesByPatient(user.getUsername());
        }

        return therapies;
    }

}
