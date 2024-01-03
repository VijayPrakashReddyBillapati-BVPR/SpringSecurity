package com.authserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.authserver.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

	@Query("SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role")
	List<Users> findAllWithUserRoles();
	 
	public Optional<Users> findUserById(Long id);
}
