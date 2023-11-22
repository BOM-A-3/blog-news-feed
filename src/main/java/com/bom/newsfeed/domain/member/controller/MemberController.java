package com.bom.newsfeed.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.member.dto.request.SignupRequest;
import com.bom.newsfeed.domain.member.service.MemberService;
import com.bom.newsfeed.global.common.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 API", description = "회원 API")
@RequestMapping("/api/members")
@RestController
public class MemberController {
	public final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@Operation(summary = "회원 가입", description = "회원 가입 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "회원가입 성공"
		),
		@ApiResponse(
			responseCode = "409",
			description = "회원가입 실패 - 이미 아이디 또는 닉네임이 존재하는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody SignupRequest request) {
		memberService.signup(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/nickname/verify")
	public ResponseEntity<Object> verifyNickname(
		@RequestParam("nickname") String nickname
	) {
		memberService.verifyNickname(nickname);
		return ResponseEntity.noContent().build();
	}
}
