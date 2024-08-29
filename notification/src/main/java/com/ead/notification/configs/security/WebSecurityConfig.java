package com.ead.notification.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ead.notification.configs.security.impl.AccessDeniedHandlerImpl;
import com.ead.notification.configs.security.impl.AuthenticationEntryPointImpl;
import com.ead.notification.configs.security.impl.UserDetailsServiceImpl;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    AuthenticationEntryPointImpl authenticationEntryPointImpl;
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    
	@Autowired
	AccessDeniedHandlerImpl accessDeniedHandlerImpl; 
    
    @Bean
    AuthenticationJwtFilter authenticationJwtFilter() {
        AuthenticationJwtFilter filter = new AuthenticationJwtFilter();
 //       filter.setJwtProvider(jwtProvider);
        filter.setUserDetailsServiceImpl(userDetailsServiceImpl);
        return filter;
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
                	.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // Permitir acesso a despachadores do tipo ERROR
                	.anyRequest().authenticated()
	        )
        .csrf(csrf -> csrf.disable());

        httpSecurity.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}