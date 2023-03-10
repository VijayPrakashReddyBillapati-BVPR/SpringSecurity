package com.security.practice.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name="roles")
public class Roles implements Serializable{

	private static final long serialVersionUID = 5711096610215477511L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SpringSecurity_RolesSequence")
	private Long Id;
	private String roleName;
	
	public Roles(Long id, String roleName) {
		super();
		Id = id;
		this.roleName = roleName;
	}
	public Roles() {
		super();
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
			
}
