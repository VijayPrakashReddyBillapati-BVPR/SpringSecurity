package com.authserver.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authserver.security.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${security.secretKey}")
	private String key;
	private UserDetailsService userDetailsService;
	private final JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsServiceImpl,JwtTokenFilter jwtTokenFilter) {
		super();
		this.userDetailsService = userDetailsServiceImpl;
		this.jwtTokenFilter = jwtTokenFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		int strength = 15; 
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, strength, new SecureRandom(key.getBytes()));
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http
		.authorizeHttpRequests(request -> request
				.requestMatchers("/auth/login/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("USER","ADMIN","MANAGER")
				.requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
				.anyRequest().authenticated()
				)
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
		
		http.headers(header -> header.frameOptions(frame -> frame.disable()));		
		http.httpBasic(Customizer.withDefaults());
		// Add JWT token filter
		return http.build();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
