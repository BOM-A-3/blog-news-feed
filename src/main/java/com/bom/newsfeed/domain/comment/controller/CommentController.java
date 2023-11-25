package com.bom.newsfeed.domain.comment.controller;

import static com.bom.newsfeed.global.common.constant.ResponseCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.service.CommentService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "댓글 API", description = "댓글 API")
@RestController
@RequestMapping("/api/post/{postId}/comment")
public class CommentController {
	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@Operation(summary = "댓글 작성", description = "댓글 작성 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "댓글 작성 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "댓글 작성 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PostMapping

	public ResponseEntity<SuccessResponse<Object>> createComment(@PathVariable Long postId,
																 @RequestBody CommentRequestDto commentRequestDto,
																 @CurrentMember MemberDto memberDto) {
		return ResponseEntity.status(CREATED_COMMENT.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(CREATED_COMMENT)
				.data(commentService.createComment(postId, commentRequestDto, memberDto))
				.build()
		);
}


	@Operation(summary = "댓글 수정", description = "댓글 수정 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "댓글 수정 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "댓글 수정 실패 - 해당 댓글에 대해 수정 권한이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "댓글 수정 실패 - 해당 댓글이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PutMapping("/{commentId}")

	public ResponseEntity<SuccessResponse<Object>> updateComment(@PathVariable Long commentId,
																 @RequestBody CommentRequestDto commentRequestDto,
							  									 @CurrentMember MemberDto memberDto) {
		return ResponseEntity.status(UPDATE_COMMENT.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(UPDATE_COMMENT)
				.data(commentService.updateComment(commentId, commentRequestDto, memberDto))
				.build()
			);
	}

	@Operation(summary = "댓글 삭제", description = "댓글 삭제 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "204",
			description = "댓글 삭제 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "댓글 삭제 실패 - 해당 댓글에 대해 삭제 권한이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "댓글 삭제 실패 - 해당 댓글이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@DeleteMapping("/{commentId}")

	public ResponseEntity<SuccessResponse<Object>> deleteComment(@PathVariable Long commentId,
																 @CurrentMember MemberDto memberDto) {
		return ResponseEntity.status(DELETE_COMMENT.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(DELETE_COMMENT)
				.data(commentService.deleteComment(commentId, memberDto))
				.build()
		);
	}
}
