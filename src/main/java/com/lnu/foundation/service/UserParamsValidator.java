package com.lnu.foundation.service;

import com.lnu.foundation.model.SignupForm;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;
import java.util.Optional;

@Component
public class UserParamsValidator extends LocalValidatorFactoryBean {
	
	private UserRepository userRepository;
	
	@Resource
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignupForm.class);
	}

	@Override
	public void validate(Object obj, Errors errors, final Object... validationHints) {
		
		super.validate(obj, errors, validationHints);
		
		if (!errors.hasErrors()) {
			SignupForm signupForm = (SignupForm) obj;
			Optional<User> user = userRepository.findByUsername(signupForm.getEmail());
			if (!user.isPresent())
				errors.rejectValue("email", "emailNotUnique");			
		}
		
	}

	@Override
	public ExecutableValidator forExecutables() {
		return null;
	}

	@Override
	public ParameterNameProvider getParameterNameProvider() {
		return null;
	}
}
