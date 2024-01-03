package com.authserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authserver.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {

	Optional<Roles> findByName(String roleName);

}
