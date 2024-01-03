package com.authserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Roles role;
	
	
	public UserRoles(Long id, Users user, Roles roles) {
		super();
		Id = id;
		this.user = user;
		this.role = roles;
	}
	
	public UserRoles(Users user, Roles roles) {
		super();
		this.user = user;
		this.role = roles;
	}
	public UserRoles() {
		super();
	}
	
	

	public Long getId() {
		return Id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles roles) {
		this.role = roles;
	}
}
