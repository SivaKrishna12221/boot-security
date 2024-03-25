package com.nt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource ds;

	@Bean
	public PasswordEncoder createEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public UserDetailsService createUserDetails() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(ds);
		/*jdbcUserDetailsManager.setUsersByUsernameQuery("select username,password,enabled from users where username=?");
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("select authority,username from authorities where username=?");
		*/// if we create a table with exact column names and table names this methods are
			// not required

		return jdbcUserDetailsManager;
	}

	@Bean
	public SecurityFilterChain authorizationConfig(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> req.anyRequest().authenticated()).formLogin(form -> form.permitAll());
		return http.build();
	}

}
