package com.security.practice.config;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.practice.model.UserRoles;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

//	@Query(value = "select r.role_name from roles r join user_roles ur on r.id = ur.role_id where ur.email = ?", nativeQuery = true)
	@Query(value = "select role_id from user_roles where email = ?", nativeQuery = true)
	List<Long> findByEmail(String email);
}
