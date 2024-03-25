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

	@Autowired(required = false)
	private DataSource ds;

	@Bean
	public UserDetailsService createDS() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(ds);
		// if we use exact columns in the user table no need to write the queries here
		jdbcUserDetailsManager.setUsersByUsernameQuery("select uname,pwd,status from system.users where uname=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select uname,role from system.user_roles where uname=?");
		return jdbcUserDetailsManager;
	}

	@Bean // it is required for the authentication provider when we make it as bean the
			// authentication provider use this.
	public PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*@Bean
	public DaoAuthenticationProvider createProvider(UserDetailsService service, PasswordEncoder encoder) {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(service);
		daoAuthProvider.setPasswordEncoder(encoder);
		return daoAuthProvider;
	}*/

	@Bean
	public SecurityFilterChain authorizationConfigure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(req -> req.requestMatchers("/emp/home").permitAll().requestMatchers("/emp/add_emp")
				.hasRole("admin").requestMatchers("/emp/view_project").hasRole("employee").anyRequest().authenticated())
				.formLogin(form -> form// default is /login with an HTTP GET
						/*.loginPage("/login")*/// this is for custom login page
						.loginProcessingUrl("/perform_login") // default is /login with an HTTP POST
						.defaultSuccessUrl("/emp/home", true).failureUrl("/login?error=true").permitAll());
		return http.build();

	}

}
