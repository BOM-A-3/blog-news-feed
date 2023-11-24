package com.bom.newsfeed.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bom.newsfeed.global.filter.JwtAuthenticationFilter;
import com.bom.newsfeed.global.filter.JwtAuthorizationFilter;
import com.bom.newsfeed.global.security.AuthenticationEntryPointImpl;
import com.bom.newsfeed.global.security.UserDetailsServiceImpl;
import com.bom.newsfeed.global.util.JwtUtil;
import com.bom.newsfeed.global.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final ObjectMapper objectMapper;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf(AbstractHttpConfigurer::disable);

		// 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
		http.sessionManagement(sessionManagement ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		http.authorizeHttpRequests(authorizeHttpRequests ->
			authorizeHttpRequests
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
				.requestMatchers(HttpMethod.GET, "/api/post/**").permitAll()
				.requestMatchers(WHITE_LIST_URL).permitAll()
				.anyRequest().authenticated() // 그 외 모든 요청 인증처리
		);

		http
			.exceptionHandling(handle ->
				handle
					.authenticationEntryPoint(authenticationEntryPoint())
			);

		// 필터 관리
		http.addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, redisUtil, userDetailsService);
	}
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(){
		return new AuthenticationEntryPointImpl(objectMapper);
	}
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, redisUtil, objectMapper);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}


	private static final String[] WHITE_LIST_URL = {
		"/api/auth/**",
		"/api/members/signup",

		//swagger
		"/v1/api-docs/**",
		"/v2/api-docs",
		"/v3/api-docs",
		"/v3/api-docs/**",
		"/swagger-resources",
		"/swagger-resources/**",
		"/configuration/ui",
		"/configuration/security",
		"/swagger-ui/**",
		"/webjars/**",
		"/swagger-ui.html"
	};
}
