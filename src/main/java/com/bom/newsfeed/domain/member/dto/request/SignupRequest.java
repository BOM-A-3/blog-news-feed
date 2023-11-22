package com.bom.newsfeed.domain.member.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bom.newsfeed.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class SignupRequest {
	private String username;
	private String password;
	private String nickname;

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return Member.of(
			username,
			passwordEncoder.encode(password),
			nickname
		);
	}
}
