package com.bom.newsfeed.domain.member.dto.response;

import com.bom.newsfeed.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
	@Schema(description = "회원 ID", example = "1")
	private final Long memberId;

	@Schema(description = "로그인아이디", example = "username1")
	private final String username;
	@Schema(description = "닉네임", example = "봄봄봄")
	private final String nickname;

	public static SignupResponse from(Member entity) {
		return new SignupResponse(
			entity.getId(),
			entity.getUsername(),
			entity.getNickname()
		);
	}
}
