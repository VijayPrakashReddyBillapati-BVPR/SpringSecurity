package com.authserver.services;

import java.util.List;
import java.util.Optional;

import com.authserver.model.Users;
import com.authserver.model.dto.UserRequest;


public interface UserDataService {

	public Optional<Users> getUsers(Long id);
	public List<Users> getUsers();
	public Users saveUser(UserRequest newUser);
	public Users updateUser(UserRequest newUser);
	void deleteUser(Long userId);
	
}
