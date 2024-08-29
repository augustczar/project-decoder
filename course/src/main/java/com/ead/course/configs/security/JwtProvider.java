package com.ead.course.configs.security;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtProvider {
	
	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;
	
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
	
	public String getSubjectJwt(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey()).build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public String getClaimNameJwt(String token, String claimName) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get(claimName).toString();
	}

	public boolean validateJwt(String  authToken) throws SignatureException {
		try {
			Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(authToken);
			return true;
		} catch (SecurityException e) {
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
