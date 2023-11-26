package com.bom.newsfeed.domain.follow.controller;

import static com.bom.newsfeed.global.common.constant.ResponseCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.follow.service.FollowService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "팔로우 API", description = "팔로우 API 컨트롤러")
@RequestMapping("/api/follow")
@RestController
public class FollowController {

	private final FollowService followService;

	public FollowController(FollowService followService) {
		this.followService = followService;
	}


	@Operation(summary = "팔로우 등록", description = "팔로우 등록 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "팔로우 등록 API",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "팔로우할 회원의 정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "자기 자신을 팔로우 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "팔로우 등록",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "이미 팔로우한 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	//팔로우 등록
	@PostMapping("/{followingId}")
	public ResponseEntity<SuccessResponse> follow(
		@Parameter(name = "followingId", description = "팔로우 할 회원 ID", in = ParameterIn.PATH)
		@PathVariable(name = "followingId") Long followingId,
		@CurrentMember MemberDto memberDto
	) {
		followService.follow(followingId, memberDto);
		return ResponseEntity.ok(
			SuccessResponse.builder()
				.responseCode(ADD_FOLLOW)
				.build()
		);
	}




	@Operation(summary = "팔로우 취소", description = "팔로우 취소 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "팔로우 취소 API",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "팔로우 취소할 회원의 정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "팔로우 정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	//팔로우 취소
	@DeleteMapping("/{followingId}")
	public ResponseEntity<SuccessResponse> unFollow(
		@Parameter(name = "followingId", description = "팔로우 할 회원 ID", in = ParameterIn.PATH)
		@PathVariable(name = "followingId") Long followingId,
		@CurrentMember MemberDto memberDto
	) {
		followService.unFollow(followingId, memberDto);
		return ResponseEntity.ok(
			SuccessResponse.builder()
				.responseCode(DELETE_FOLLOW)
				.build()
		);
	}
}
