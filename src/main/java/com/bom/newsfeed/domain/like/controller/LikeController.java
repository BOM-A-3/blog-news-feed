package com.bom.newsfeed.domain.like.controller;

import static com.bom.newsfeed.global.common.constant.ResponseCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.like.service.LikeService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "좋아요 API", description = "좋아요 API")
@RestController
@RequestMapping("/api")
public class LikeController {
	private final LikeService likeService;
	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@Operation(summary = "좋아요 등록", description = "좋아요 등록 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "좋아요 등록 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "204",
			description = "좋아요 등록 실패 - 해당 게시글이 없는 경우",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		)
	})
	@PostMapping("/post/{postId}/like")
	public ResponseEntity<Object> addLike(@PathVariable Long postId,
														   @CurrentMember MemberDto memberDto) {
		likeService.addLike(postId, memberDto);
		return ResponseEntity.ok(SuccessResponse.builder()
			.responseCode(ADD_LIKE)
			.build());

	}

	@Operation(summary = "좋아요 취소", description = "좋아요 취소 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "좋아요 취소 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "204",
			description = "좋아요 취소 실패 - 해당 게시글이 없는 경우",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		)
	})
	@DeleteMapping("/post/{postId}/like")
	public ResponseEntity<Object> deleteLike(@PathVariable Long postId,
															  @CurrentMember MemberDto memberDto) {

		likeService.deleteLike(postId, memberDto);
		return ResponseEntity.ok(SuccessResponse.builder()
			.responseCode(DELETE_LIKE)
			.build());

	}
}
