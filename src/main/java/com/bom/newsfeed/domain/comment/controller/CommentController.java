package com.bom.newsfeed.domain.comment.controller;

import com.bom.newsfeed.global.common.dto.DefaultRes;
import com.bom.newsfeed.global.common.dto.ResponseMessage;
import com.bom.newsfeed.global.common.dto.StatusCode;
import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.service.CommentService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// 댓글 작성
	@PostMapping
	public ResponseEntity createComment(@PathVariable Long postId,
										@RequestBody CommentRequestDto commentRequestDto,
										@CurrentMember MemberDto memberDto
							  ) {
		commentService.createComment(postId, commentRequestDto, memberDto);
		return ResponseEntity.ok(DefaultRes.res(StatusCode.CREATE, ResponseMessage.CREATED_COMMENT));
	}

	// 댓글 수정
	@PutMapping("/{commentId}")
	public ResponseEntity updateComment(@PathVariable Long commentId,
										@RequestBody CommentRequestDto commentRequestDto,
							  			@CurrentMember MemberDto memberDto) {

		commentService.updateComment(commentId, commentRequestDto, memberDto);
		return ResponseEntity.ok(DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_COMMENT));
	}

	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity deleteComment(@PathVariable Long commentId,
										@CurrentMember MemberDto memberDto) {

		commentService.deleteComment(commentId, memberDto);
		return ResponseEntity.ok(DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_COMMENT));
	}
}