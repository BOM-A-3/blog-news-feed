package com.bom.newsfeed.domain.member.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bom.newsfeed.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
	@Schema(
		description = "로그인 아이디",
		example = "봄봄봄"
	)
	@Pattern(
		regexp = "[a-zA-Z0-9_]+",
		message = "특수문자 _ 와 영어 대소문자 및 숫자만 입력가능합니다."
	)
	@Size(
		min = 4,
		max = 20,
		message = "최소 4자 이상 최대 20자 이하로만 입력가능합니다."
	)
	@NotBlank(message = "아이디를 입력해주세요.")
	private final String username;

	@Schema(description = "로그인 비밀번호", example = "password12")
	@Pattern(
		regexp = "^[a-zA-Z0-9]+$",
		message = "비밀번호는 알파벳 대/소문자, 숫자의 조합으로 입력해야합니다."
	)
	@Size(
		min = 8,
		max = 20,
		message = "비밀번호는 8자리 이상, 20자리 이하로 입력해야합니다."
	)
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private final String password;

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
	@NotBlank(message = "닉네임을 입력해주세요.")
	private final String nickname;

	public Member toEntity(PasswordEncoder passwordEncoder) {
		return Member.of(
			username,
			passwordEncoder.encode(password),
			nickname
		);
	}
}
