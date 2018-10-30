package com.lnu.foundation.controller;

import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by rucsac on 20/10/2018.
 */
@Controller
public class LoggedInUserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("loggedInUser")
    public void secureUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        model.addAttribute("loggedInUser", user);
    }

    @GetMapping("/secure/user")
    public String securePage() {
        return "secure/user";
    }

}
