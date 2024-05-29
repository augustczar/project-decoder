package com.ead.authuser.configs.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ead.authuser.configs.security.impl.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtProvider {
	
	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;
	
	@Value("${ead.auth.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwt(Authentication authentication) {
		UserDetails userDetailsPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject((userDetailsPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		
	}
	
	public String getUserNameJwt(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwt(String  authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {} ", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {} ", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {} ", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {} ", e.getMessage());
		}catch (IllegalArgumentException e) {
			log.error("JWT clains string is empty: {} ", e.getMessage());
		}
		return false;
	}
}
