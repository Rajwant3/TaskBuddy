package com.task.buddy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(authz -> authz
				.requestMatchers("/register", "/", "/h2-console/**", "/login", "/about","/js/**", "/images/**","/css/**", "/webjars/**")
				.permitAll()
				.requestMatchers("/profile/**", "/tasks/**", "/task/**", "/users", "/user/**", "/h2-console/**")
				.hasAnyAuthority("USER", "ADMIN").requestMatchers("/assignment/**").hasAuthority("ADMIN").anyRequest()
				.authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/login").usernameParameter("email")
						.defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/login"));
		return http.build();
	}

}
