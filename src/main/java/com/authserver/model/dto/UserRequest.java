package com.authserver.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

	@JsonProperty(required = true)
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	private Long id;
	
	@NotBlank(message = "Password cannot be blank")
	@JsonProperty(required = true)
	private String password;

	private Set<String> roleNames;

	private boolean passwordChanged = false;

	public UserRequest(String name, String password, Set<String> roleNames) {
		super();
		this.name = name;
		this.password = password;
		this.roleNames = roleNames;
		this.passwordChanged = false;
	}

	public UserRequest(Long id, String name, String password, Set<String> roleNames) {
		super();
		this.name = name;
		this.id = id;
		this.password = password;
		this.roleNames = roleNames;
		this.passwordChanged = false;
	}
	
	public UserRequest(Long id, String name, String password, Set<String> roleNames, boolean passwordChanged) {
		super();
		this.name = name;
		this.id = id;
		this.password = password;
		this.roleNames = roleNames;
		this.passwordChanged = passwordChanged;
	}
	
	

	public UserRequest(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.passwordChanged = false;
	}

	public UserRequest() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoleNames() {
		
		if(roleNames == null) {
			return new HashSet<String>();
		}
		return roleNames;
	}

	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	@Override
	public String toString() {
		return "UserRequest [" + (name != null ? "name=" + name + ", " : "") + (id != null ? "id=" + id + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (roleNames != null ? "roleNames=" + roleNames + ", " : "") + "passwordChanged=" + passwordChanged
				+ "]";
	}
	

}
