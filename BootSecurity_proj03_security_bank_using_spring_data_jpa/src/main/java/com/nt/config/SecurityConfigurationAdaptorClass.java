package com.nt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationAdaptorClass {

	@Autowired
	private UserDetailsService userDetails;

	@Autowired
	private BCryptPasswordEncoder encoder;

	// here DaoAuthenticationProvider internally uses UserDetailsService and
	// encoder;
	// optional
	//// usernmae,password->login ->a.filter ->a.manager uses ->a.provider ->uses
	// UserDetails
	@Bean
	private DaoAuthenticationProvider createDaoAuthProvider() {
		DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
		daoAuth.setUserDetailsService(userDetails);
		daoAuth.setPasswordEncoder(encoder);
		return daoAuth;
	}

	@Bean("securityFilterChain")
	@SuppressWarnings("all")
	public SecurityFilterChain createChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> req.requestMatchers("/bank/").permitAll().requestMatchers("/bank/offers")
				.authenticated().requestMatchers("/bank/loan_approval").hasRole("manager")
				.requestMatchers("/bank/check_balance").hasRole("customer").requestMatchers("/user/register")// new User
																												// registration
				.permitAll())
				// custom form login activities
				.formLogin(login -> login.loginPage("/user/show_login").permitAll().loginProcessingUrl("/login")
						.defaultSuccessUrl("/bank/", true).failureUrl("/user/show_login?error"))
				// exception handling error
				// .exceptionHandling(exce -> exce.accessDeniedPage("/denied"))

				.logout(log -> log.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
						.logoutSuccessUrl("/user/show_login?logout"))// custom logout page->login page with success
				// logou
				.sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true);
		return http.build();
	}
	/*
	 * DaoAuthenticationProvider
	DaoAuthenticationProvider is an AuthenticationProvider implementation that uses a UserDetailsService and PasswordEncoder to authenticate a username and password.
	
	This section examines how DaoAuthenticationProvider works within Spring Security. The following figure explains the workings of the AuthenticationManager in figures from the Reading the Username & Password section.
	
	daoauthenticationprovider
	Figure 1. DaoAuthenticationProvider Usage
	number 1
		
		The authentication
		Filter from
		the Reading
		the Username&
		Password section
		passes a
		UsernamePasswordAuthenticationToken to
		the AuthenticationManager, which
		is implemented
		by ProviderManager.
		
		number 2
		The ProviderManager
		is configured
		to use
		an AuthenticationProvider
		of type DaoAuthenticationProvider.
		
		number 3
		DaoAuthenticationProvider looks
		up the
		UserDetails from
		the UserDetailsService.
		
		number 4
		DaoAuthenticationProvider uses
		the PasswordEncoder
		to validate
		the password
		on the
		UserDetails returned
		in the
		previous step.
		
		number 5
		When authentication
		is successful, the
		Authentication that
		is returned
		is of
		type UsernamePasswordAuthenticationToken
		and has
		a principal
		that is
		the UserDetails
		returned by
		the configured UserDetailsService.Ultimately,
		the returned
		UsernamePasswordAuthenticationToken is
		set on
		the SecurityContextHolder
		by the
		authentication Filter.*/
	/*SecurityContextHolder
	At the heart of Spring Security’s authentication model is the SecurityContextHolder. It contains the SecurityContext.
	
	securitycontextholder
	The SecurityContextHolder is where Spring Security stores the details of who is authenticated. Spring Security does not care how the SecurityContextHolder is populated. If it contains a value, it is used as the currently authenticated user.
	
	The simplest way to indicate a user is authenticated is to set the SecurityContextHolder directly:
	
	Setting SecurityContextHolder
	Java
	
	Kotlin
	
	SecurityContext context = SecurityContextHolder.createEmptyContext(); 
	Authentication authentication =
	new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
	context.setAuthentication(authentication);
	
	SecurityContextHolder.setContext(context); 
	We start by creating an empty SecurityContext. You should create a new SecurityContext instance instead of using SecurityContextHolder.getContext().setAuthentication(authentication) to avoid race conditions across multiple threads.
	Next, we create a new Authentication object. Spring Security does not care what type of Authentication implementation is set on the SecurityContext. Here, we use TestingAuthenticationToken, because it is very simple. A more common production scenario is UsernamePasswordAuthenticationToken(userDetails, password, authorities).
	Finally, we set the SecurityContext on the SecurityContextHolder. Spring Security uses this information for authorization.
	To obtain information about the authenticated principal, access the SecurityContextHolder.
	
	Access Currently Authenticated User
	Java
	
	Kotlin
	
	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	String username = authentication.getName();
	Object principal = authentication.getPrincipal();
	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	B y default, SecurityContextHolder uses a ThreadLocal to store these details, which means that the SecurityContext is always available to methods in the same thread, even if the SecurityContext is not explicitly passed around as an argument to those methods. Using a ThreadLocal in this way is quite safe if you take care to clear the thread after the present principal’s request is processed. Spring Security’s FilterChainProxy ensures that the SecurityContext is always cleared.
	
	Some applications are not entirely suitable for using a ThreadLocal, because of the specific way they work with threads. For example, a Swing client might want all threads in a Java Virtual Machine to use the same security context. You can configure SecurityContextHolder with a strategy on startup to specify how you would like the context to be stored. For a standalone application, you would use the SecurityContextHolder.MODE_GLOBAL strategy. Other applications might want to have threads spawned by the secure thread also assume the same security identity. You can achieve this by using SecurityContextHolder.MODE_INHERITABLETHREADLOCAL. You can change the mode from the default SecurityContextHolder.MODE_THREADLOCAL in two ways. The first is to set a system property. The second is to call a static method on SecurityContextHolder. Most applications need not change from the default. However, if you do, take a look at the JavaDoc for SecurityContextHolder to learn more.*/
}