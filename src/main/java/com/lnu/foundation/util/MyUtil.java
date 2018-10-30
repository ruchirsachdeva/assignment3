package com.lnu.foundation.util;

import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.User;
import com.lnu.foundation.service.SecurityContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;
import java.util.Optional;

@Component
public class MyUtil {

    private static final Logger logger = LoggerFactory.getLogger(MyUtil.class);

    private static MessageSource messageSource;
    private static TokenHandler tokenHandler;
    private static SecurityContextService securityContextService;

    @Autowired
    public  void setTokenHandler(TokenHandler tokenHandler) {
        MyUtil.tokenHandler = tokenHandler;
    }

    @Autowired
    public  void setSecurityContextService(SecurityContextService securityContextService) {
        MyUtil.securityContextService = securityContextService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {

        MyUtil.messageSource = messageSource;

    }

    public static String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, Locale.getDefault());
    }

    public static void flash(RedirectAttributes redirectAttributes,
                             String kind, String messageKey) {

        redirectAttributes.addFlashAttribute("flashKind", kind);
        redirectAttributes.addFlashAttribute("flashMessage",
                /**MyUtil.getMessage(messageKey)**/ messageKey);

    }

    public static Optional<String> logInUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return token;
        });
    }

    public static boolean isAuthorized(Facebook facebook) {
        try {
            return facebook.isAuthorized();
        } catch (Throwable t){
            return false;
        }
    }

    public static void authenticate(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        String username = userProfile.getUsername();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.debug("User {} {} connected.", userProfile.getFirstName(), userProfile.getLastName());
    }

}

