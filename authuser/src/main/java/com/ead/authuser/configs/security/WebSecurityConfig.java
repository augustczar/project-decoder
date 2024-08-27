package com.ead.authuser.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ead.authuser.configs.security.impl.AccessDeniedHandlerImpl;
import com.ead.authuser.configs.security.impl.AuthenticationEntryPointImpl;
import com.ead.authuser.configs.security.impl.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

	private static final String[] AUTH_WHITELIST = {
			"/auth/**"
	};
	
	@Value("${ead.serviceRegistry.username}")
	private String userName;
	
	@Value("${ead.serviceRegistry.password}")
	private String password;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPointImpl;
	
	@Autowired
	AccessDeniedHandlerImpl accessDeniedHandlerImpl;  
	
	@Bean
	AuthenticationJwtFilter authenticationJwtFilter() {
		return new AuthenticationJwtFilter();
	}
	@Bean
	RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		String hierarchy = "ROLE_ADMIN > ROLE_INSTRUCTOR \n ROLE_INSTRUCTOR > ROLE_STUDENT \n ROLE_STUDENT > ROLE_USER";
		roleHierarchyImpl.setHierarchy(hierarchy);
		return roleHierarchyImpl;
	}
	
	@Bean
	DefaultMethodSecurityExpressionHandler expressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy());
		return expressionHandler;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.exceptionHandling(exceptionHandling -> 
				exceptionHandling
					.authenticationEntryPoint(authenticationEntryPointImpl)
					.accessDeniedHandler(accessDeniedHandlerImpl)
			)
	          
	        .sessionManagement(sessionManagement -> 
	            sessionManagement
	            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        )
	        .authorizeHttpRequests(authorize -> 
	            authorize
	                .requestMatchers(AUTH_WHITELIST).permitAll()
	                .anyRequest().authenticated()
	        )
	        .csrf(csrf -> csrf.disable());

	    httpSecurity.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);

	    return httpSecurity.build();
	}

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
