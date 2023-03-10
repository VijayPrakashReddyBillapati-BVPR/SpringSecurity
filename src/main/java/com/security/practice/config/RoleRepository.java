package com.security.practice.config;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.practice.model.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	public List<Roles> findByIdIn(List<Long> id);
}
