package com.nt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder createEncoder() {
		System.out.println("SecurityConfig.createEncoder()");

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain configAuthorization(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig.configAuthorization()");

		http.authorizeHttpRequests(req -> req.requestMatchers("/user/save", "/emp/home").permitAll()
				.requestMatchers("/emp/add_emp").hasRole("ADMIN").requestMatchers("/emp/view_project")
				.hasRole("EMPLOYEE").requestMatchers("/emp/assign_project").hasRole("MANAGER"))
				.formLogin(form -> form.permitAll());
		return http.build();

	}
}
