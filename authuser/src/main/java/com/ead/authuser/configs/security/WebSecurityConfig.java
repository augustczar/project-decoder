package com.ead.authuser.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ead.authuser.configs.security.impl.AuthenticationEntryPointImpl;
import com.ead.authuser.configs.security.impl.UserDetailsServiceImpl;

import jakarta.ws.rs.HttpMethod;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
	AuthenticationEntryPointImpl authenticationEntryPoint;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.httpBasic()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.authorizeHttpRequests()
			.requestMatchers(AUTH_WHITELIST).permitAll()
			.requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.formLogin();
		return httpSecurity.build();
	}
/*	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		UserDetails userDetails = User.withUsername(userName)
				.password(passwordEncoder().encode(password))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
*/
	
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
