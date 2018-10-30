package com.lnu.foundation.service;

import com.lnu.foundation.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rucsac on 18/11/2016.
 */

@Service
public class SocialUserService {
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private UserService socialUserDetailsService;
    @Autowired
    private SecurityContextService securityContextService;


    private static final Logger logger = LoggerFactory.getLogger(SocialUserService.class);

    public Connection<?> getConnection(String provider,String accessToken) {
        OAuth2ConnectionFactory connectionFactory = (OAuth2ConnectionFactory) connectionFactoryLocator.getConnectionFactory(provider);
        return connectionFactory.createConnection(new AccessGrant(accessToken));
    }


    public Optional<User> authenticateSocialUser(Connection<?> connection) {
        SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(connection, null);
        Authentication authentication;
        try {
            authentication = new SocialAuthenticationProvider(usersConnectionRepository, socialUserDetailsService).authenticate(socialAuthenticationToken);
        } catch (Exception e) {
            return Optional.empty();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserProfile userProfile = connection.fetchUserProfile();
        logger.debug("User {} {} authenticated.", userProfile.getFirstName(), userProfile.getLastName());
        return securityContextService.currentUser();
    }


    public Connection<Google> getGoogleConnection(String accessToken) {
        GoogleConnectionFactory connectionFactory = (GoogleConnectionFactory) connectionFactoryLocator.getConnectionFactory("google");
        return connectionFactory.createConnection(new AccessGrant(accessToken));
    }

    public void connectSocial(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        usersConnectionRepository.createConnectionRepository(userProfile.getEmail()).addConnection(connection);
        logger.debug("User {} {} connection created.", userProfile.getFirstName(), userProfile.getLastName());
    }

    public void disconnectSocial(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        usersConnectionRepository.createConnectionRepository(userProfile.getEmail()).removeConnection(connection.getKey());
        logger.debug("User {} {} connection deleted.", userProfile.getFirstName(), userProfile.getLastName());
    }

    public Optional<User> authenticateSocialUser(String accessToken) {
        return authenticateSocialUser(getGoogleConnection(accessToken));
    }

    public Optional<User> postUserSignUp(Optional<String> accessToken) {
        return accessToken.map(a -> {
            Connection<?> connection = getGoogleConnection(a);
            connectSocial(connection);
            return authenticateSocialUser(connection).get();
        });
    }
}
