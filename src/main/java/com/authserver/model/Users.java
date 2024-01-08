package com.authserver.model;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"userRoles"})
@SequenceGenerator(name = "user_roles", sequenceName = "user_sequence", initialValue = 1000, allocationSize = 1)
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@Nonnull
	private Long Id;

	@NotBlank
	@Nonnull
	@Size(min = 3, message =  "name should have atleast 3 characters")
	private String name;

	@Column(nullable = false, length = 60) // Increase the length to accommodate BCrypt hashed passwords
	@NotBlank
	@Nonnull
	@Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters") // Update max length
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<UserRoles> userRoles;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	@JsonIgnore
	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}
	
	public Set<String> getRoles() {
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());
    }

	public Users(String name, String password, Set<UserRoles> roles) {
		super();
		this.name = name;
		this.password = password;
		this.userRoles = roles;
	}

	public Users() {
		super();
	}

	public Users(Long id, String name, String password, Set<UserRoles> roles) {
		super();
		Id = id;
		this.name = name;
		this.password = password;
		this.userRoles = roles;
	}

	@Override
	public String toString() {
		return "Users [Id=" + Id + ", name=" + name + ", password=" + password+" ]";
	}

}
