package com.lnu.foundation.service;

import com.lnu.foundation.model.*;
import com.lnu.foundation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@Service
public class UserService implements UserDetailsService, SocialUserDetailsService {

    private static final String PATIENT = "patient";
    private static final String RESEARCHER = "researcher";
    private static final String PHYSICIAN = "physician";
    @Autowired
    private UserRepository repository;

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    TestSessionRepository testRepository;

    @Autowired
    TherapyRepository therapyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserconnectionRepository userconnectionRepository;

    public List<User> getPatient() {
        return repository.findByRole(roleRepository.findByName(PATIENT));
    }

    public List<User> getResearcher() {
        return repository.findByRole(roleRepository.findByName(RESEARCHER));
    }

    public List<User> getPhysician() {
        return repository.findByRole(roleRepository.findByName(PHYSICIAN));
    }

    public Collection<Therapy> getMedTherapies(User med) {
        return therapyRepository.findByMed_Username(med.getEmail());
    }

    public boolean isMedLoggedIn() {
        return isPhysicianLoggedIn() || isResearcherLoggedIn();
    }

    private boolean isPhysicianLoggedIn() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(Google.class) != null;
    }

    private boolean isResearcherLoggedIn() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(LinkedIn.class) != null;
    }

    public Collection<TestSession> getSessions(String username) {
        return testRepository.findByTest_Therapy_Med_Username(username);
    }

    public Collection<TestSession> getPatientSessions(String username) {
        return testRepository.findByTest_Therapy_Patient_Username(username);
    }

    public List<Therapy> getTherapiesByPatient(String username) {
        return therapyRepository.findByPatient_Username(username);
    }

    public List<Therapy> getTherapiesByMed(String username) {
        return therapyRepository.findByMed_Username(username);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User signup(SignupForm signupForm) {
        final User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setUsername(signupForm.getEmail());
        user.setFirstName(signupForm.getName());
        if (signupForm.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        }
        user.setProvider(signupForm.getProvider());
        repository.save(user);
        return user;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        repository.save(user);
        return user;
    }


    // Will be called after form based authentication to fetch user details
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.lnu.foundation.model.User user = repository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("LOGGED_USER"));
        //new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities)
        return user;


    }

    // Will be called after social authentication to fetch user details
    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException, DataAccessException {
        Userconnection userconnection = userconnectionRepository.findOne(userId);

        if (userconnection != null && "google".equals(userconnection.getProviderId())) {
            List<User> physicians = getPhysician();
            if (!CollectionUtils.isEmpty(physicians)) {
                return physicians.get(0);
            }
        }
        if (userconnection != null && "linkedin".equals(userconnection.getProviderId())) {
            List<User> patient = getPatient();
            if (!CollectionUtils.isEmpty(patient)) {
                return patient.get(0);
            }
        }
        if (userconnection != null && "facebook".equals(userconnection.getProviderId())) {
            List<User> researcher = getResearcher();
            if (!CollectionUtils.isEmpty(researcher)) {
                return researcher.get(0);
            }
        }
        return repository.findAll().get(0);
    }
}
