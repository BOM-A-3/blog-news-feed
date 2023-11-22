package com.bom.newsfeed.domain.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.dto.CommentResponseDto;
import com.bom.newsfeed.domain.comment.service.CommentService;
import com.bom.newsfeed.domain.member.entity.Member;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// 댓글 리스트 조회
	@GetMapping()
	public List<CommentResponseDto> getFindAll() {
		return commentService.getFindAll();
	};

	// 댓글 작성
	@PostMapping()
	public CommentRequestDto createComment(@RequestBody CommentRequestDto commentRequestDto) {
		return commentService.createComment(commentRequestDto);
	}

	// 댓글 수정
	@PutMapping("/{commentId}")
	public void updateComment(@RequestBody CommentRequestDto commentRequestDto) {
		return commentService.updateComment(commentRequestDto);
	}

	// 댓글 삭제
}
