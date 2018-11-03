package com.lnu.foundation.controller;


import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import com.lnu.foundation.service.SecurityContextService;
import lombok.Value;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private SecurityContextService securityContextService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/api/auth/social"}, method = RequestMethod.POST)
    public AuthResponse loginToSocial(@RequestBody(required = false) AuthParams params) throws AuthenticationException {

        if ("linkedin".equals(params.getProvider())) {
            User researcher = userRepository.findByRole_Name("researcher").get(0);
            final String token = tokenHandler.createTokenForUser(researcher);
            return new AuthResponse(token);

        }

        if ("google".equals(params.getProvider())) {
            User physician = userRepository.findByRole_Name("physician").get(0);
            final String token = tokenHandler.createTokenForUser(physician);
            return new AuthResponse(token);
        }

        if ("facebook".equals(params.getProvider())) {
            User patient = userRepository.findByRole_Name("patient").get(0);
            final String token = tokenHandler.createTokenForUser(patient);
            return new AuthResponse(token);
        }


        return null;
    }

    @RequestMapping(value = {"/", "/login"})
    public AuthResponse login() {
        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.OPTIONS)
    public ResponseEntity loginOption() {
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/api/auth"}, method = RequestMethod.POST)
    public AuthResponse auth(@RequestBody(required = false) AuthParams params) throws AuthenticationException {

        if (params != null) {
            final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
            final Authentication authentication = authenticationManager.authenticate(loginToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return securityContextService.currentUser().map(u -> {
            String token;
            if (u.getRole() != null && "patient".equals(u.getRole().getName())) {
                token = tokenHandler.createTokenForUser(u);
            } else {
                User patient = userRepository.findByRole_Name("patient").get(0);
                token = tokenHandler.createTokenForUser(patient);

            }
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }

    /**
     * If we can't find a user/email combination
     */
    @RequestMapping("/login-error")
    public ResponseEntity loginError(Model model) {
        model.addAttribute("loginError", true);
        return ResponseEntity.badRequest().build();
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
