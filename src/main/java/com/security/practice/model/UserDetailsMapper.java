package com.security.practice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsMapper {

	private String userName;
	private String email;
	private String password;
	private boolean isActive;
	private List<GrantedAuthority> roles;
	
	
	public UserDetailsMapper(User user, List<String> userRoles) {
		this.userName = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.isActive = user.isEnabled();
		roles = new ArrayList<GrantedAuthority>();
		for(String userRole: userRoles) {
			GrantedAuthority authority = new SimpleGrantedAuthority(userRole);
			roles.add(authority);
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return isActive;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}
}
