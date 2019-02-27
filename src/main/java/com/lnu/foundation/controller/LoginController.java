package com.lnu.foundation.controller;


import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.SignupForm;
import com.lnu.foundation.model.User;
import com.lnu.foundation.service.SecurityContextService;
import com.lnu.foundation.service.UserService;
import lombok.Value;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private SecurityContextService securityContextService;

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @RequestMapping(value = {"/api/auth/social"}, method = RequestMethod.POST)
    public AuthResponse loginToSocial(@RequestBody(required = false) AuthParams params) throws AuthenticationException {

        if ("linkedin".equals(params.getProvider())) {
            User researcher = userService.findByRole_Name("researcher");
            final String token = tokenHandler.createTokenForUser(researcher);
            return new AuthResponse(token);

        }

        if ("google".equals(params.getProvider())) {
            User physician = userService.findByRole_Name("physician");
            final String token = tokenHandler.createTokenForUser(physician);
            return new AuthResponse(token);
        }

        if ("facebook".equals(params.getProvider())) {
            User patient = userService.findByRole_Name("patient");
            final String token = tokenHandler.createTokenForUser(patient);
            return new AuthResponse(token);
        }


        return null;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @RequestMapping(value = {"/api/auth"}, method = RequestMethod.POST)
    public AuthResponse login(@RequestBody(required = false) AuthParams params) throws AuthenticationException {

        if (params != null) {
            final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
            securityContextService.authenticate(loginToken);
        }
        return securityContextService.currentUser().map(u -> {
            String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }

    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @RequestMapping(value = {"/api/auth/create"}, method = RequestMethod.POST)
    public AuthResponse create(@RequestBody(required = false) SignupForm params) throws AuthenticationException {

        if (params != null) {
            this.userService.signup(params);
            final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
            securityContextService.authenticate(loginToken);
        }
        return securityContextService.currentUser().map(u -> {
            String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }


    @RequestMapping(value = {"/", "/login"})
    public AuthResponse auth() {
        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }


    @CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.OPTIONS)
    public ResponseEntity authOption() {
        return ResponseEntity.ok().build();
    }


    @Value
    private static final class AuthParams {
        private final String username;
        private final String password;
        private final String provider;
        private final String token;


        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }

    @Value
    private static final class AuthResponse {
        private final String token;
    }

}
