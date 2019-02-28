package com.lnu.foundation.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupForm {
	
	@NotNull
	@Size(min=1, max=255)
	@Email
	private String email;

	private String username;
	
	@NotNull
	@Size(min=1, max=100, message="{nameSizeError}")
	private String name;
	
	@NotNull
	@Size(min=1, max=30)
	private String password;

	private String provider;

	private String accessToken;

	private Double latitude;
	private Double longitude;


	@Override
	public String toString() {
		return "SignupForm [email=" + email + ", name=" + name + ", password="
				+ password + "]";
	}
	

	public UsernamePasswordAuthenticationToken toAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
	
}
