package com.ead.notificationhex.adapters.configs.security;

import java.security.SignatureException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
	Logger log = LogManager.getLogger(JwtProvider.class);
	
	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;
	
	public String getSubjectJwt(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getClaimNameJwt(String token, String claimName) {
		return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().get(claimName).toString();
	}

	public boolean validateJwt(String  authToken) throws SignatureException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
			return true;
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
