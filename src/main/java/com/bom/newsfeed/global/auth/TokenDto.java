package com.bom.newsfeed.global.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TokenDto {
	private String accessToken;
	private String refreshToken;

	public TokenDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static TokenDto of(String accessToken, String refreshToken) {
		return new TokenDto(accessToken, refreshToken);
	}

}
