package com.bom.newsfeed.global.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bom.newsfeed.global.security.UserDetailsServiceImpl;
import com.bom.newsfeed.global.util.JwtUtil;
import com.bom.newsfeed.global.util.RedisUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String tokenValue = jwtUtil.getTokenFromRequestHeader(request);

		if (StringUtils.hasText(tokenValue)) {
			Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
			String logOutToken = redisUtil.getKey("Logout:" + info.getSubject());

			//Logout 토큰 검증
			if (!StringUtils.hasText(logOutToken) || !tokenValue.equals(logOutToken)) {
				setAuthentication(info.getSubject());
			}
		}

		filterChain.doFilter(request, response);
	}
	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}