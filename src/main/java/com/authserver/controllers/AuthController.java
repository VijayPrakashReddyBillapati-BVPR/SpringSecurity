package com.authserver.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authserver.exception.CustomResponse;
import com.authserver.model.Users;
import com.authserver.model.dto.UserRequest;
import com.authserver.security.jwt.JwtTokenUtil;
import com.authserver.services.UserDataService;

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
	public ResponseEntity<?> login(@Valid @RequestBody UserRequest loginRequest, BindingResult bindingResult) {

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
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			response.put("message", "Login successful");
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse("User not found"));
		}
	}
}
