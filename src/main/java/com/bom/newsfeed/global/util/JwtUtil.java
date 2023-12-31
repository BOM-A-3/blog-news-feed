package com.bom.newsfeed.global.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.global.auth.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 관련 로그")
@Component
public class JwtUtil {

	// Header KEY 값
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	// 사용자 권한 값의 KEY
	private static final String AUTHORIZATION_KEY = "auth";
	// Token 식별자
	private static final String BEARER_PREFIX = "Bearer ";

	// 토큰 만료시간
	@Value("${jwt.access-token-expiration}")
	public Long accessTokenExpiration; // 60분

	@Value("${jwt.refresh-token-expiration}")
	public Long refreshTokenExpiration; // 하루

	@Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	// 토큰 생성
	public TokenDto createToken(String username, MemberRole role) {
		Date date = new Date();

		String accessToken =
			Jwts.builder()
				.setSubject(username) // 사용자 식별자값(ID)
				.claim(AUTHORIZATION_KEY, role) // 사용자 권한
				.setExpiration(new Date(date.getTime() + accessTokenExpiration)) // 만료 시간
				.setIssuedAt(date) // 발급일
				.signWith(key, signatureAlgorithm) // 암호화 알고리즘
				.compact();

		String refreshToken =
			Jwts.builder()
				.setSubject(username) // 사용자 식별자값(ID)
				.claim(AUTHORIZATION_KEY, role) // 사용자 권한
				.setExpiration(new Date(date.getTime() + refreshTokenExpiration)) // 만료 시간
				.setIssuedAt(date) // 발급일
				.signWith(key, signatureAlgorithm) // 암호화 알고리즘
				.compact();

		return TokenDto.of(accessToken, refreshToken);
	}

	// JWT 토큰 substring
	public String substringToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(7);
		}
		log.error("Not Found Token");
		throw new NullPointerException("Not Found Token");
	}

	// 토큰 검증
	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
		}
	}

	// 토큰에서 사용자 정보 가져오기
	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String decodeTokenString(String encodedToken) throws UnsupportedEncodingException {
		return URLDecoder.decode(encodedToken, "utf-8");
	}

	// HttpServletRequest 에서 Cookie Value : JWT 가져오기
	public String getTokenFromRequestHeader(HttpServletRequest req) {
		String tokenValue = req.getHeader(AUTHORIZATION_HEADER);
		if (!StringUtils.hasText(tokenValue)) {
			return null;
		}
		// JWT 토큰 substring
		tokenValue = substringToken(tokenValue);
		validateToken(tokenValue);

		return tokenValue;
	}


	public void setHeaderAccessToken(String token, HttpServletResponse response) {
		response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token);
		response.setStatus(HttpStatus.OK.value());
	}

	public String getRefreshTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		String token = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(REFRESH_TOKEN_HEADER)) {
				try {
					token = URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
				} catch (UnsupportedEncodingException e) {
					break;
				}
			}
		}
		return substringToken(token);
	}

	public void setTokenResponse(TokenDto tokenDto, HttpServletResponse response) {
		setHeaderAccessToken(tokenDto.getAccessToken(), response);
		setCookieRefreshToken(tokenDto.getRefreshToken(), response);
	}

	public void setCookieRefreshToken(String token, HttpServletResponse res) {
		try {
			token = URLEncoder.encode(BEARER_PREFIX + token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

			Cookie cookie = new Cookie(REFRESH_TOKEN_HEADER, token); // Name-Value
			cookie.setSecure(true);
			cookie.setHttpOnly(true);
			cookie.setPath("/");

			// Response 객체에 Cookie 추가
			res.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
	}

	public Integer getRemainingTimeMin(Date expiration) {
		Date now = new Date();
		return Math.toIntExact((expiration.getTime() - now.getTime()) / 60 / 1000);
	}
}