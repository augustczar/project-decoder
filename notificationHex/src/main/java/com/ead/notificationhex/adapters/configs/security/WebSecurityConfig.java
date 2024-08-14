package com.ead.notificationhex.adapters.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

    AuthenticationEntryPointImpl authenticationEntryPoint;
    
    @Bean
    AuthenticationJwtFilter authenticationJwtFilter() {
    	return new AuthenticationJwtFilter();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            	//.requestMatchers(HttpMethod.GET, "/ead-notification/users/**/notifications").permitAll()
            	//.requestMatchers(HttpMethod.PUT, "/ead-notification/users/**/notifications").permitAll()            
            	//.requestMatchers(HttpMethod.POST, "/notifications/**").permitAll() // Permitir acesso às rotas de notificações
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable();

        httpSecurity.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_INSTRUCTOR \n ROLE_INSTRUCTOR > ROLE_STUDENT \n ROLE_STUDENT > ROLE_USER";
        roleHierarchyImpl.setHierarchy(hierarchy);
        return roleHierarchyImpl;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}