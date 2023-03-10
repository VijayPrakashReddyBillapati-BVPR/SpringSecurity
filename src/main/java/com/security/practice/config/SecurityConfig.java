package com.security.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
    private CustomAuthenticationProvider authenticationProvider;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors().disable()
		.authorizeHttpRequests()
		.requestMatchers("/emps/**").hasRole("ADMIN")
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/users").authenticated()
		.and()
		.httpBasic()
		.and()
		.authenticationProvider(authenticationProvider)
		.build();		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
