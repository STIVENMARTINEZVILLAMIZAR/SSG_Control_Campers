package com.campus.campus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final UsuarioDetailsService usuarioDetailsService;

	public SecurityConfig(UsuarioDetailsService usuarioDetailsService) {
		this.usuarioDetailsService = usuarioDetailsService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authenticationProvider(authenticationProvider(usuarioDetailsService, passwordEncoder()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/registro", "/css/**", "/h2-console/**").permitAll()
						.requestMatchers("/dashboard/**", "/modulos/**").authenticated()
						.requestMatchers("/api/**").authenticated()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/dashboard", true)
						.permitAll())
				.logout(logout -> logout
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
				.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
