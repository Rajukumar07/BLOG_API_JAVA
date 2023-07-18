package com.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.app.security.CustomUserDetailService;
import com.blog.app.security.JwtAuthenticationEntryPoint;
import com.blog.app.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String[] PUBLIC_URLS = {
			"/api/auth/**" ,
			"/v2/api-docs",
			"/v3/api-docs",
			"/swagger-ui/**",
			"/webjars/**"
			};

	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeHttpRequests().antMatchers("/blog/api/auth/login").permitAll().anyRequest()
//				.authenticated().and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		return http.build();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// ("/api/auth/**")means all API give to access
		// ("/api/auth/login") means only login api allow to access

		http.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(PUBLIC_URLS).permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(customUserDetailService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

//	@Bean
//	public void cofigure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
