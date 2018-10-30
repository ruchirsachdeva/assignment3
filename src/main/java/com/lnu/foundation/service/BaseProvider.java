package com.lnu.foundation.service;

import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;

@Configuration
@Scope(value = "request",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BaseProvider {

	@Autowired
	private Facebook facebook;
	@Autowired
	private Google google;
	@Autowired
	private LinkedIn linkedIn;
	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	protected Autologin autologin;


	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public ConnectionRepository getConnectionRepository() {
		return connectionRepository;
	}

	public void setConnectionRepository(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	public Google getGoogle() {
		return google;
	}

	public void setGoogle(Google google) {
		this.google = google;
	}

	public LinkedIn getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(LinkedIn linkedIn) {
		this.linkedIn = linkedIn;
	}

	protected void saveUserDetails(User userBean) {
		if (StringUtils.isNotEmpty(userBean.getPassword())) {
			userBean.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
		}
		userRepository.save(userBean);

	}

	public void autoLoginUser(User userBean) {
		autologin.setSecuritycontext(userBean);
	}


}
