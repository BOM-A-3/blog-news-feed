package com.bom.newsfeed.domain.auth.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.global.auth.TokenDto;
import com.bom.newsfeed.global.exception.InvalidRefreshTokenException;
import com.bom.newsfeed.global.util.JwtUtil;
import com.bom.newsfeed.global.util.RedisUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Transactional
@Service
public class AuthService {
	private static final String PREFIX_REFRESH_TOKEN = "RefreshToken:";
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	public AuthService(JwtUtil jwtUtil, RedisUtil redisUtil) {
		this.jwtUtil = jwtUtil;
		this.redisUtil = redisUtil;
	}

	public void reissue(HttpServletRequest request, HttpServletResponse response) {
		String targetToken = jwtUtil.getRefreshTokenFromCookie(request);

		//토큰 검증
		jwtUtil.validateToken(targetToken);

		//토큰에서 username 추출
		Claims claims = jwtUtil.getUserInfoFromToken(targetToken);
		String username = claims.getSubject();
		MemberRole role = MemberRole.valueOf((String) claims.get("auth"));

		//redis에서 refresh token 확인
		String refreshToken = redisUtil.getKey(PREFIX_REFRESH_TOKEN + username);

		//refresh token 일치하는지 검증
		if (!StringUtils.hasText(refreshToken) || !refreshToken.equals(targetToken)) {
			throw new InvalidRefreshTokenException();
		}

		//토큰 재발급
		TokenDto tokenDto = jwtUtil.createToken(username, role);
		jwtUtil.setTokenResponse(tokenDto, response);

		//재발급된 토큰 정보 저장
		redisUtil.saveKey(PREFIX_REFRESH_TOKEN + username, 24 * 60, tokenDto.getRefreshToken());
	}

	public void logout(HttpServletRequest request) {
		String targetToken = jwtUtil.getTokenFromRequestHeader(request);

		jwtUtil.validateToken(targetToken);

		//토큰에서 username, expiration 추출
		Claims claims = jwtUtil.getUserInfoFromToken(targetToken);
		String username = claims.getSubject();
		Date expiration = claims.getExpiration();
		Integer remainingExpTime = jwtUtil.getRemainingTimeMin(expiration);

		//redis에 refresh토큰 삭제, logout token 저장 -> 인가 필터에서 꺼내서 확인
		redisUtil.deleteKey(PREFIX_REFRESH_TOKEN + username);
		redisUtil.saveKey("Logout:" + username, remainingExpTime, targetToken);
	}
}
