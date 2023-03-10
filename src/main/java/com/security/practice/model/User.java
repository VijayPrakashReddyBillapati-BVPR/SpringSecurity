package com.security.practice.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "Users")
public class User  implements Serializable{

	private static final long serialVersionUID = 4896248151362249069L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SpringSecurity_UserIdSequence")
	private Long Id;
	private String username;
	private String email;
	private String password;
	private boolean enabled;
	
	public User(Long id,String username, String email, String password, boolean enabled) {
		super();
		this.Id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User() {
		super();
	}
	public User(User user) {
		this.Id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
