package com.ead.course.configs.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthenticationJwtFilter extends OncePerRequestFilter {

	@Autowired
    private JwtProvider jwtProvider;
	
	@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            String jwtStr = getTokenHeader(request);
            log.debug("JWT Token: {}", jwtStr);
            if (jwtStr != null && jwtProvider.validateJwt(jwtStr)) {
            	log.debug("JWT Token is valid");
                String userId = jwtProvider.getSubjectJwt(jwtStr);
                String rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles");
                
                log.debug("User ID: {}", userId);
                log.debug("Roles: {}", rolesStr);
                
                UserDetails userDetails = userDetailsServiceImpl.build(UUID.fromString(userId), rolesStr);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                log.debug("JWT Token is not valid");
            }
        } catch (Exception e) {
            logger.error("Cannot set User Authentication: {} ", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}