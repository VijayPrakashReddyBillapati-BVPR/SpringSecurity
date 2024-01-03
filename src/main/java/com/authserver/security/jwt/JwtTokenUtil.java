package com.authserver.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.authserver.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtTokenUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Value("${security.secretKey}")
	private String secret;

	@Value("${jwt.expiration}")
	private long jwtExpirationInMs;
	

    private final JwtParser jwtParser;
    private final JwtBuilder jwtBuilder;

    @Autowired
    public JwtTokenUtil(JwtParser jwtParser, JwtBuilder jwtBuilder) {
        this.jwtParser = jwtParser;
        this.jwtBuilder = jwtBuilder;
    }
    
    public String createToken(Users user, Set<String> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", user.getId());
		claims.put("username", user.getName());
		claims.put("roles", roles);


        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationInMs);

        return jwtBuilder
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .compact();
    }
	
	public Map<String, Object> parseToken(String token) {
        Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);

        Claims claims = claimsJws.getPayload();

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("userId", claims.get("userId", Long.class));
        tokenInfo.put("username", claims.get("username", String.class));
        tokenInfo.put("roles", claims.get("roles", ArrayList.class));
        tokenInfo.put("expiryDate", claims.getExpiration());
        return tokenInfo;
    }

	public boolean isTokenExpired(String token) {
		Date expirationDate = (Date) parseToken(token).get("expiryDate");
		return expirationDate.before(new Date());
	}

	// Method to check if a token is valid
	public boolean isTokenValid(String token) {
		
		Map<String, Object> parseToken = parseToken(token);
		Date expirationDate = (Date) parseToken.get("expiryDate");
		Long userId = (Long) parseToken.get("userId");
		logger.info("pased the token with UserId: {} and isExpired: {}", userId, expirationDate.before(new Date()));
		return userId != null && !expirationDate.before(new Date());

	}

	public Long getUserId(String token) {
		return (Long) parseToken(token).get("userId");
	}
}
