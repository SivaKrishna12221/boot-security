package com.nt.securityConfig;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationClass {
	// this is resonsble to convert the plane text to secured text->user send
	// password and it should convert and to match with userdetails
	@Bean
	public PasswordEncoder createBcrypt() {
		return new BCryptPasswordEncoder();
	}

	// The Authentication provider uses UserDetailsService(having already data) and
	// password encoder;

	// it is a interface having only one method that returs UserDetails object
	// having data -> User is a class it can provide the implementation for
	// UserDetails.

	// Authentication provider serches for the UserDetailsService obj.so he were are
	// creating object for it
	@Bean
	public UserDetailsService createUserDetails(PasswordEncoder encoder) {

		// here we can create UserDetails Objects ->User impl class
		/*
		
		* Builds the user to be added. At minimum the username, password, and authorities
		* should provided. The remaining attributes have reasonable defaults.
		*
		public static final class UserBuilder
		==========
		 public static UserBuilder withUsername(String username) {
		return builder().username(username);
		}
		public UserBuilder username(String username) {
			Assert.notNull(username, "username cannot be null");
			this.username = username;
			return this;
		}
		
		*/
		UserDetails admin = User.withUsername("vivek").password(encoder.encode("arya")).roles("admin").build();
		UserDetails manager = User.withUsername("siva").password(encoder.encode("sitha")).roles("manager").build();
		UserDetails employee = User.withUsername("sitha").password(encoder.encode("ram")).roles("employee").build();
		return new InMemoryUserDetailsManager(Arrays.asList(admin, manager, employee));
	}

	@Bean
	public SecurityFilterChain configAuthorization(HttpSecurity http) throws Exception {
		/*return http.csrf().disable().authorizeHttpRequests().requestMatchers("/emp/home").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/emp/add_emp").hasRole("admin").and().authorizeHttpRequests()
				.requestMatchers("/emp/view_project").hasRole("employee").and().authorizeHttpRequests()
				.requestMatchers("/emp/assign_project").hasRole("manager").and().authorizeHttpRequests().anyRequest()
				.authenticated().and().formLogin().and().build();
		//this authorizeHttpRequest() is deprecated from 6.1 version onwards
		*/

		http.authorizeHttpRequests(req -> req.requestMatchers("/emp/home").permitAll().requestMatchers("/emp/add_emp")
				.hasRole("admin").requestMatchers("/emp/view_project").hasRole("employee").anyRequest().authenticated())
				.formLogin(form -> form// default is /login with an HTTP GET
						/*.loginPage("/login")*/// this is for custom login page
						.loginProcessingUrl("/perform_login") // default is /login with an HTTP POST
						.defaultSuccessUrl("/emp/home", true).failureUrl("/login?error=true").permitAll());
		return http.build();

	}

	/*
	 * the basic flow of authentication using Spring Security when a user submits their username and password. Spring Security is a powerful and customizable authentication and access-control framework for Java applications, especially used in the context of Spring applications. The process I'm going to describe is simplified to focus on the main components involved in the authentication flow:
	
	User Submission: The journey begins when the user submits their credentials (usually a username and password) through a login form.
	
	Authentication Filter: This submission is intercepted by a specific filter in Spring Security, typically the UsernamePasswordAuthenticationFilter. This filter catches the login request and attempts to extract the username and password from the request.
	
	Authentication Token Creation: The filter creates an Authentication object, more specifically, an instance of UsernamePasswordAuthenticationToken. At this stage, the token is considered "unauthenticated" because it hasn't been checked against the user details service or the password encoder yet. This object contains the principal (usually the username) and credentials (password).
	
	Authentication Manager: The unauthenticated token is passed to the AuthenticationManager, which acts as a facade for various AuthenticationProvider instances. The AuthenticationManager is responsible for orchestrating the authentication process.
	
	Authentication Provider: The AuthenticationManager delegates the token to the appropriate AuthenticationProvider, such as DaoAuthenticationProvider for a database-backed user store. This provider is responsible for the actual verification of the user's credentials.
	
	UserDetailsService and PasswordEncoder: The AuthenticationProvider uses a UserDetailsService to retrieve the UserDetails object, which contains the stored information of the user (username, password, authorities, and other details). It then uses a PasswordEncoder to compare the submitted password (after encoding it) with the stored password associated with the user account.
	
	Authentication Success or Failure:
	
	If the credentials are valid, the AuthenticationProvider creates a fully populated Authentication object (now considered authenticated), including the granted authorities (permissions/roles associated with the user).
	If the authentication process fails (e.g., wrong username/password, account locked, etc.), it throws an AuthenticationException.
	Security Context: Upon successful authentication, the authenticated Authentication object is stored in the SecurityContextHolder, which holds the details of the principal currently using the application. This context is accessible throughout the application, allowing for security decisions and access control.
	
	Success or Failure Handlers: Depending on the outcome, Spring Security routes the flow to either a success handler (e.g., redirecting to a home page) or a failure handler (e.g., redirecting back to the login page with an error message).
	
	Session Management: If authentication succeeds, Spring Security may also manage session creation and set up a SecurityContext persistence across requests, typically using HTTP session or similar mechanisms.
	
	Access to Protected Resources: Once authenticated, the user can request protected resources within the application. Each request is intercepted by the security filter chain, which checks the SecurityContext to ensure that the user is authenticated and has the necessary authorities to access the requested resource.
	
	This flow can be customized extensively through Spring Security configurations to suit different authentication mechanisms (like OAuth2, JWT, LDAP, etc.), password encoding schemes, user details services, and more. Additionally, Spring Security supports adding various filters and handlers to enrich or alter the authentication and authorization processes to meet complex security requirements.
	 */

}
