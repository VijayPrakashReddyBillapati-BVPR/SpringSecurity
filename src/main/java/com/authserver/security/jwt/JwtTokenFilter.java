package com.authserver.security.jwt;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Filtering the Incoming request {}", request.getServletPath());
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (isEmpty(header) || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.isTokenValid(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		logger.info("Incoming token Validated Successfully");

		// Get user identity and set it on the spring security context
		UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(jwtTokenUtil.getUserId(token)));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails == null ? List.of() : userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		filterChain.doFilter(request, response);
	}

	private boolean isEmpty(String header) {
		if (header == null || header == "") {
			return true;
		}
		return false;
	}
}
