package com.bom.newsfeed.domain.post.controller;

import java.time.chrono.IsoEra;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.service.PostService;
import com.bom.newsfeed.global.annotation.CurrentMember;

import lombok.Getter;

@RequestMapping("/api")
@RestController
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/post")
	public ResponseEntity<?> createPost(@CurrentMember MemberDto memberDto,
										@RequestBody PostRequestDto postRequestDto){
		return ResponseEntity.ok(postService.createPost(postRequestDto,memberDto));
	}


	@GetMapping("/post")
	public ResponseEntity<?> getAllPost() {
		return ResponseEntity.ok(postService.getAllPost());
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<?> selectPost(@PathVariable Long id){

		return ResponseEntity.ok(postService.selectPost(id));

	}

	@PutMapping("/post/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id,
										@CurrentMember MemberDto memberDto,
										@RequestBody PostRequestDto postRequestDto) throws IllegalAccessException{
		return ResponseEntity.ok(postService.updatePost(id,memberDto, postRequestDto));
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id, @CurrentMember MemberDto memberDto) throws IllegalAccessException{
		return ResponseEntity.ok(postService.deletePost(id, memberDto));
	}


}
