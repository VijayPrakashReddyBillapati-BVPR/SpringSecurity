package com.authserver.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "roles")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Nonnull
	private Long Id;
	
	@Nonnull
	private String name; 
	
	@OneToMany( mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<UserRoles> userRoles;

	public Long getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRoles> getRoles() {
		return userRoles;
	}

	public void setRoles(Set<UserRoles> roles) {
		this.userRoles = roles;
	}

	public Roles(Long id, String name, Set<UserRoles> roles) {
		super();
		Id = id;
		this.name = name;
		this.userRoles = roles;
	}
	
	public Roles(String name) {
		super();
		this.name = name;
	}
	
	public Roles(String name, Set<UserRoles> roles) {
		super();
		this.name = name;
		this.userRoles = roles;
	}
	public Roles() {
		super();
	}
}
