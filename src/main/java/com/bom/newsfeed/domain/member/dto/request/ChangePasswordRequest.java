package com.bom.newsfeed.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {
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
