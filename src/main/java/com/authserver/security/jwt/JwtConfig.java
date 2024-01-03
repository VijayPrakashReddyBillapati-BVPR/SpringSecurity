package com.authserver.security.jwt;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfig {

    @Value("${security.secretKey}")
    private String secret;

    @Bean
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Bean
    public JwtParser jwtParser() {
    	return Jwts.parser().setSigningKey(key()).build();
    }
    
    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder()
                .signWith(key());
    }

}