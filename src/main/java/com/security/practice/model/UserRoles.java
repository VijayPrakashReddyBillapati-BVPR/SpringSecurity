package com.security.practice.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "UserRoles")
public class UserRoles  implements Serializable{

	private static final long serialVersionUID = -4605826035863771365L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SpringSecurity_UserRolesSequence")
	private Long userRoleId;
	private String email;
	private Long RoleId;

	
	public UserRoles(Long userRoleId, String email, Long roleId) {
		super();
		this.userRoleId = userRoleId;
		this.email = email;
		this.RoleId = roleId;
	}

	public UserRoles() {
		super();
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRoleId() {
		return RoleId;
	}

	public void setRoleId(Long roleId) {
		RoleId = roleId;
	}	
}
