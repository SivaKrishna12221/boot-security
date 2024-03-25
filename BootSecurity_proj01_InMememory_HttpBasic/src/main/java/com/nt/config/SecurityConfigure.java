package com.nt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager detailsManager(PasswordEncoder encoder) {
		UserDetails manager = User.withUsername("siva").password(encoder.encode("sitha")).roles("manager").build();
		UserDetails customer = User.withUsername("sitha").password(encoder.encode("ram")).roles("customer").build();
		return new InMemoryUserDetailsManager(manager, customer);
	}

	@Bean // by default the the deligatingFilterProxy contain the logical view as the
			// sringSecurityFilterChain
	@SuppressWarnings("all")
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/bank/home").permitAll().and().authorizeRequests()
				.antMatchers("/bank/balance").hasRole("customer").and().authorizeRequests().antMatchers("/bank/offers")
				.hasAnyRole("manager", "customer").anyRequest().authenticated().and().formLogin()

				.defaultSuccessUrl("/bank/home").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/bank/signout")).and().exceptionHandling()
				.accessDeniedPage("/bank/denied").and().sessionManagement().maximumSessions(2)// one user can login only
																								// one
																								// time .no chance to
																								// login
																								// in ant
				.maxSessionsPreventsLogin(true);// if i keep true it can allow two logins only

		return http.build();
	}
}
