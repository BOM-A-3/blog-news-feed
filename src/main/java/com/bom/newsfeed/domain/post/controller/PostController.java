package com.bom.newsfeed.domain.post.controller;

import java.time.chrono.IsoEra;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.service.PostService;
import com.bom.newsfeed.global.annotation.CurrentMember;

@RequestMapping("/api")
@RestController
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/post")
	public ResponseEntity<?> createCard(@CurrentMember MemberDto memberDto,
										@RequestBody PostRequestDto postRequestDto){
		return ResponseEntity.ok(postService.createPost(postRequestDto,memberDto));
	}


}
