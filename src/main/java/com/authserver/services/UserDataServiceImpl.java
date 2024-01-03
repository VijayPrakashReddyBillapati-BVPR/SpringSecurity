package com.authserver.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authserver.model.Roles;
import com.authserver.model.UserRoles;
import com.authserver.model.Users;
import com.authserver.model.dto.UserRequest;
import com.authserver.repository.RolesRepository;
import com.authserver.repository.UserRolesRepository;
import com.authserver.repository.UsersRepository;

@Service
public class UserDataServiceImpl implements UserDataService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDataServiceImpl.class);
	
	
	private UsersRepository usersRepository;
	private RolesRepository rolesRepository; 
	private UserRolesRepository userRolesRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDataServiceImpl(UsersRepository usersRepository,RolesRepository rolesRepository,UserRolesRepository userRolesRepository) {
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
		this.userRolesRepository = userRolesRepository;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<Users> getUsers(Long id) {
		return usersRepository.findById(id);
	}

	@Override
	@Transactional
	public List<Users> getUsers() {
		return usersRepository.findAllWithUserRoles();
	}


	@Override
	public Users saveUser(UserRequest newUser) {
		logger.info("saving user details {}",newUser.toString());
		 Users user = new Users();
	        user.setName(newUser.getName());
	        user.setPassword( passwordEncoder .encode(newUser.getPassword()));
	        user.setUserRoles(new HashSet<>()); // Initialize userRoles with an empty set
	        if(newUser.getId() != null) {
	        	user.setId(newUser.getId());
	        }
	        
	        if(newUser == null || newUser.getRoleNames().isEmpty()) {
	        	Roles defaultRole = rolesRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found"));
	            UserRoles userRoles = new UserRoles();
	            userRoles.setUser(user);
	            userRoles.setRole(defaultRole);
	            user.getUserRoles().add(userRoles);
	        } else {
	        	 for (String roleName : newUser.getRoleNames()) {
	        		 Roles role = rolesRepository.findByName(roleName.toUpperCase())
	                         .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
	 	        
	 	            UserRoles userRoles = new UserRoles();
	 	            userRoles.setUser(user);
	 	            userRoles.setRole(role);
	 	            user.getUserRoles().add(userRoles);
	 	        }
	        }
	        return usersRepository.save(user);
	}


	@Override
	public Users updateUser(UserRequest updateUser) {
		logger.info("updating user details {}",updateUser.toString());
	    Long userId = updateUser.getId();

	    // Check if the user with the given ID exists
	    Users existingUser = usersRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    // Update the user's information
	    existingUser.setName(updateUser.getName());
	    if(updateUser.isPasswordChanged())
	    	existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
	    else {
	    	existingUser.setPassword(updateUser.getPassword());
	    }
	    

	    // Update user roles
	    existingUser.getUserRoles().clear(); // Remove existing roles

	    if (updateUser.getRoleNames() == null || updateUser.getRoleNames().isEmpty()) {
	        // If no roles are specified, assign a default role
	        Roles defaultRole = rolesRepository.findByName("USER")
	                .orElseThrow(() -> new RuntimeException("Default role not found"));
	        UserRoles userRoles = new UserRoles();
	        userRoles.setUser(existingUser);
	        userRoles.setRole(defaultRole);
	        existingUser.getUserRoles().add(userRoles);
	    } else {
	        // Add specified roles
	        for (String roleName : updateUser.getRoleNames()) {
	            Roles role = rolesRepository.findByName(roleName.toUpperCase())
	                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
	            UserRoles userRoles = new UserRoles();
	            userRoles.setUser(existingUser);
	            userRoles.setRole(role);
	            existingUser.getUserRoles().add(userRoles);
	        }
	    }

	    // Save the updated user
	    return usersRepository.save(existingUser);
	}
	
	@Override
	@Transactional
	public void deleteUser(Long userId) {
	    // Check if the user with the given ID exists
	    Users existingUser = usersRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	    // Delete associated UserRoles
	    userRolesRepository.deleteByUser(existingUser);
	    usersRepository.deleteById(userId);
	}

}
