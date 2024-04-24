package com.ead.authuser.configs.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

	private static final String[] AUTH_WHITELIST = {
			"/ead-authuser/auth/**"
	};
	
	@Value("${ead.serviceRegistry.username}")
	private String userName;
	
	@Value("${ead.serviceRegistry.password}")
	private String password;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.httpBasic()
			.and()
			.authorizeHttpRequests()
			.requestMatchers(AUTH_WHITELIST).permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.formLogin();
		return httpSecurity.build();
	}
	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		UserDetails userDetails = User.withUsername(userName)
				.password(passwordEncoder().encode(password))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
