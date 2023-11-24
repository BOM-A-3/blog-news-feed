package com.bom.newsfeed.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileRequest {
	@Schema(
		description = "닉네임",
		example = "봄봄봄"
	)
	@Pattern(
		regexp = "[a-zA-Z0-9_]+",
		message = "특수문자 _ 와 영어 대소문자 및 숫자만 입력가능합니다."
	)
	@Size(
		min = 4,
		max = 30,
		message = "최소 4자 이상 최대 30자 이하로만 입력가능합니다."
	)
	private final String nickname;


	@Schema(
		description = "한 줄 소개",
		example = "안녕하세요~"
	)
	@Size(
		max = 50,
		message = "최대 50자 이하로만 입력가능합니다."
	)
	private final String introduce;

	@Schema(description = "변경할 비밀번호", example = "password12")
	@Pattern(
		regexp = "^[a-zA-Z0-9]+$",
		message = "비밀번호는 알파벳 대/소문자, 숫자의 조합으로 입력해야합니다."
	)
	@Size(
		min = 8,
		max = 20,
		message = "비밀번호는 8자리 이상, 20자리 이하로 입력해야합니다."
	)
	private final String changePassword;

	@Schema(description = "변경할 비밀번호 확인", example = "password12")
	@Pattern(
		regexp = "^[a-zA-Z0-9]+$",
		message = "비밀번호는 알파벳 대/소문자, 숫자의 조합으로 입력해야합니다."
	)
	@Size(
		min = 8,
		max = 20,
		message = "비밀번호는 8자리 이상, 20자리 이하로 입력해야합니다."
	)
	private final String confirmPassword;
}
