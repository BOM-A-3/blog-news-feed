package com.bom.newsfeed.domain.member.dto.response;

import com.bom.newsfeed.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchProfileResponse {
	@Schema(description = "회원 ID", example = "1")
	private final Long memberId;
	@Schema(description = "닉네임", example = "봄봄봄")
	private final String nickname;

	@Schema(description = "한 줄 소개", example = "안녕하세요~")
	private final String introduce;

	public static SearchProfileResponse from(Member member) {
		return new SearchProfileResponse(
			member.getId(),
			member.getNickname(),
			member.getIntroduce()
		);
	}
}
