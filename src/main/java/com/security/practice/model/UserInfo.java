package com.security.practice.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {

	private static final long serialVersionUID = 4990044801566984427L;
	private String username;
	private String email;
	@Transient
	private String password;
	private boolean isActive;
	private List<GrantedAuthority> roles;
	
	public UserInfo(UserDetailsMapper userDetails) {
		this.username = userDetails.getUserName();
		this.email = userDetails.getEmail();
		this.password = userDetails.getPassword();
		this.isActive = userDetails.isActive();
		this.roles = userDetails.getRoles();
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
	
	public String getEmail() {
		return email;
	}

}
