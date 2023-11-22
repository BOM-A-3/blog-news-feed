package com.bom.newsfeed.domain.member.controller;

import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.dto.request.SignupRequest;
import com.bom.newsfeed.domain.member.dto.request.UpdateProfileRequest;
import com.bom.newsfeed.domain.member.dto.response.UpdateProfileResponse;
import com.bom.newsfeed.domain.member.service.MemberService;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

	@Operation(summary = "닉네임 중복검사", description = "닉네임 중복검사 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "204",
			description = "중복 없음 확인"
		),
		@ApiResponse(
			responseCode = "409",
			description = "닉네임이 존재하는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@GetMapping("/nickname/verify")
	public ResponseEntity<Object> verifyNickname(
		@Parameter(name = "nickname", description = "닉네임")
		@RequestParam("nickname") String nickname
	) {
		memberService.verifyNickname(nickname);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "아이디 중복검사", description = "아이디 중복검사 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "204",
			description = "중복 없음 확인"
		),
		@ApiResponse(
			responseCode = "409",
			description = "아이디가 존재하는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@GetMapping("/username/verify")
	public ResponseEntity<Object> verifyUsername(
		@Parameter(name = "username", description = "아이디")
		@RequestParam("username") String username
	) {
		memberService.verifyUsername(username);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "프로필 수정", description = "프로필 수정 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "프로필 수정 API",
			content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "수정할 회원 정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "회원 정보에 대해 수정 권한이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PutMapping("/{memberId}/profile")
	public void updateProfile (
		@Parameter(name = "memberId", description = "회원 ID")
		@PathVariable("memberId") Long memberId,
		@RequestBody UpdateProfileRequest request,
		@CurrentMember MemberDto memberDto
	) {
		ResponseEntity.ok(memberService.updateProfile(memberId, request, memberDto));
	}
}
