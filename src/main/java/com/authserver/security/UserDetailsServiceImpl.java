package com.authserver.security;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authserver.model.Roles;
import com.authserver.model.UserRoles;
import com.authserver.model.Users;
import com.authserver.repository.UserRolesRepository;
import com.authserver.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${security.secretKey}")
	private String key;
	
	private UserRolesRepository userRolesRepository;
	private UsersRepository usersRepository;
	
	UserDetailsServiceImpl(UserRolesRepository userRolesRepository, UsersRepository usersRepository) {
		this.userRolesRepository = userRolesRepository;
		this.usersRepository = usersRepository;
	}
	
	public PasswordEncoder passwordEncoder() {
		int strength = 15; 
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, strength, new SecureRandom(key.getBytes()));
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Users> findById = usersRepository.findUserById(Long.valueOf(username));        
		
		UserDetails userDetails = findById.map(user -> {
			List<UserRoles> findByUserId = userRolesRepository.findByUserId(user.getId());
			
			Set<String> roles = findByUserId.stream()
			.map(UserRoles::getRole)
			.map(Roles::getName)
			.collect(Collectors.toSet());
			
			return User.builder()
			.username(String.valueOf(user.getId()))
			.password(user.getPassword())
			.roles(roles.stream().toArray(String[]::new)).build();
		}).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		
		return userDetails;
	}

}
