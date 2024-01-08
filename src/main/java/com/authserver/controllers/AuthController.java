package com.authserver.controllers;

import java.util.Optional;
import java.util.Set;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.exception.CustomResponse;
import com.authserver.exception.UserNotFoundException;
import com.authserver.model.Users;
import com.authserver.model.dto.UserRequest;
import com.authserver.security.jwt.JwtTokenUtil;
import com.authserver.services.UserDataService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDataService userService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,UserDataService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserRequest loginRequest, BindingResult bindingResult,HttpServletRequest request, HttpServletResponse response) {

		logger.info("logging user with username: {}",loginRequest.getName());
		
		if (bindingResult.hasErrors()) {
			logger.error("Login request has validation errors: {}", bindingResult.getAllErrors());
			return ResponseEntity.badRequest().body(new CustomResponse("Validation error. Check your input."));
		}
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

		Optional<Users> user = userService.getUsers(loginRequest.getId());

		if (user.isPresent()) {
			Set<String> roles = user.get().getRoles();
			String token = jwtTokenUtil.createToken(user.get(), roles);
			response.addHeader("Authorization", "Bearer " + token);
			return ResponseEntity.status(HttpStatus.OK).body("Login successful");
		} else {
			throw new UserNotFoundException("User not found with username: " + loginRequest.getId());
		}
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<CustomResponse> handleAuthenticationException(AuthenticationException ex){
		String errorMessage = "Authentication failed, check your login credentials";
		logger.info("Authentication failed {} "+ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomResponse(errorMessage));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<CustomResponse> handleBadCreddentials(BadCredentialsException ex) {
		String errorMessage = "Invalid Username or password";
		logger.info("Invalid Username or password {}"+ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomResponse(errorMessage));
	}
}
