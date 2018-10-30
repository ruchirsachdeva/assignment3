package com.lnu.foundation.service;

import com.lnu.foundation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GoogleProvider {

    private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/login";
    private static final String GOOGLE = "google";

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    UserService service;

    public String getGoogleUserData(Model model, User userForm) {

        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(Google.class) == null) {
            return REDIRECT_CONNECT_GOOGLE;
        }

        populateUserDetailsFromGoogle(userForm);
        //Save the details in DB
        socialLoginBean.saveUserDetails(userForm);

        //Login the User
        socialLoginBean.autoLoginUser(userForm);

        List<User> physicians = service.getPhysician();
        if (!CollectionUtils.isEmpty(physicians)) {
            User physician = physicians.get(0);
            userForm.setUsername(physician.getUsername());
            model.addAttribute("therapies", service.getMedTherapies(physician));
        }
        model.addAttribute("loggedInUser", userForm);
         return "secure/user";
        //return "physician";
    }


    protected void populateUserDetailsFromGoogle(User userform) {
        Google google = socialLoginBean.getGoogle();
        Person googleUser = google.plusOperations().getGoogleProfile();
        userform.setEmail(googleUser.getAccountEmail());
        userform.setUsername(googleUser.getAccountEmail());
        userform.setFirstName(googleUser.getGivenName());
        userform.setLastName(googleUser.getFamilyName());
        userform.setImage(googleUser.getImageUrl());
        userform.setProvider(GOOGLE);
    }

}
