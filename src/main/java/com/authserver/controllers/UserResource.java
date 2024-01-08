package com.authserver.controllers;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.exception.CustomResponse;
import com.authserver.model.Users;
import com.authserver.model.dto.UserRequest;
import com.authserver.services.UserDataService;


@RestController
@RequestMapping("/api")
public class UserResource {
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	private UserDataService userService;

    @Autowired
    UserResource(UserDataService userService) {
        this.userService = userService;
    }

	@GetMapping(path = "/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml", MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<List<Users>> getUsers() {
		logger.info("Fetching all users");
		List<Users> list = userService.getUsers();
		 logger.info("Found {} users", list.size());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
	}
	
	@GetMapping(path = "/users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml", MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
	    logger.info("Fetching user with ID: {}", id);
	    Optional<Users> user = userService.getUsers(id);
	    if (user.isPresent()) {
	        logger.info("User found with ID {}", id);
	        return ResponseEntity.ok(user.get());
	    } else {
	        logger.warn("User not found with ID: {}", id);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new CustomResponse("User with ID " + id + " not found"));
	    }
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<CustomResponse> deleteUser(@PathVariable Long id) {
	    try {
	        userService.deleteUser(id);
	        String successMessage = "User with ID " + id + " deleted successfully";
	        logger.info(successMessage);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CustomResponse(successMessage));
	    } catch (RuntimeException e) {
	    	logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(e.getMessage()));
	    }
	}

	
	@PostMapping("/users")
	public ResponseEntity<CustomResponse> addUser(@RequestBody UserRequest newUser) {
	    try {
	    	logger.error("creating user: {}", newUser.toString());
	        Users user = userService.saveUser(newUser);
	        String successMessage = "User '" + user.getName() + "' created successfully with ID: " + user.getId();
	        logger.info(successMessage);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(successMessage, user));
	    } catch (RuntimeException e) {
	    	logger.error("Error creating user: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(e.getMessage()));
	    }
	}

	@PutMapping("/users")
	public ResponseEntity<CustomResponse> updateUser(@RequestBody UserRequest newUser) {
	    try {
	        Users user = userService.updateUser(newUser);
	        String successMessage = "User '" + user.getName() + "' updated successfully with ID: " + user.getId();
	        logger.info(successMessage);
	        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(successMessage, user));
	    } catch (RuntimeException e) {
	    	logger.error("Error updating user: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse(e.getMessage()));
	    }
	}
}