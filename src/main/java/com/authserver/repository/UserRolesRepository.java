package com.authserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authserver.model.UserRoles;
import com.authserver.model.Users;


@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
	public List<UserRoles> findByUserId(Long userId);
	void deleteByUser(Users user);
}
