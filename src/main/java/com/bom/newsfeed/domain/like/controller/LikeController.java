package com.bom.newsfeed.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.like.service.LikeService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;

@RequestMapping("/api")
@RestController
public class LikeController {
	// like 갯수를 가저오는 API 만들기


	private final LikeService likeService;
	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@PostMapping("/post/{postId}/like")
	public ResponseEntity<?> addLike(@PathVariable Long postId,
									 @CurrentMember MemberDto memberDto) throws IllegalAccessException {
		return ResponseEntity.ok(likeService.addLike(postId,memberDto));
	}

	@DeleteMapping("/post/{postId}/like")
	public ResponseEntity<?> delete(@PathVariable Long postId,
									@CurrentMember MemberDto memberDto) {
		return ResponseEntity.ok(likeService.deleteLike(postId,memberDto));
	}

	@GetMapping("/post/{postId}/like/total")
	public ResponseEntity<?> getPostTotalLike(@PathVariable Long postId){
		return ResponseEntity.ok(likeService.getPostTotalLike(postId));
	}


}
