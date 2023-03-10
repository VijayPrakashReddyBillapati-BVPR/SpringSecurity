package com.security.practice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.practice.model.Roles;
import com.security.practice.model.User;
import com.security.practice.model.UserDetailsMapper;
import com.security.practice.model.UserInfo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(email);
		Optional<UserDetailsMapper> userDetailsMapper = Optional.empty();
		if(user.isPresent()) {
			List<Long> userRoles = userRoleRepository.findByEmail(email);
			List<Roles> roles = roleRepository.findByIdIn(userRoles);
			List<String> roleList = new ArrayList<String>();
			for( Roles role: roles) {
				roleList.add(role.getRoleName());
			}
			userDetailsMapper = Optional.of(new UserDetailsMapper(user.get(),roleList));
		}
		return userDetailsMapper.map(UserInfo::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
	}

}
