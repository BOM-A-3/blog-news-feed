package com.bom.newsfeed.domain.post.controller;

import java.time.chrono.IsoEra;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.dto.PostUpdateRequestDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.service.PostService;
import com.bom.newsfeed.domain.postfile.service.PostFileService;
import com.bom.newsfeed.global.annotation.CurrentMember;

import lombok.Getter;

@RequestMapping("/api")
@RestController
public class PostController {

	private final PostService postService;


	public PostController(PostService postService) {
		this.postService = postService;
	}
	// 게시글 생성
	@PostMapping(path = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createPost(@CurrentMember MemberDto memberDto,
										@RequestPart PostRequestDto postRequestDto, @RequestPart List<MultipartFile> files){

		return ResponseEntity.ok(postService.createPost(postRequestDto, memberDto, files));
	}

	// 게시글 조회
	@GetMapping("/post")
	public ResponseEntity<?> getAllPost() {
		return ResponseEntity.ok(postService.getAllPost());
	}

	// 게시글 선택조회
	@GetMapping("/post/{id}")
	public ResponseEntity<?> selectPost(@PathVariable Long id){

		return ResponseEntity.ok(postService.selectPost(id));
	}

	// 게시글 수정
	@PutMapping(path = "/post/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> updatePost(@PathVariable Long id,
										@CurrentMember MemberDto memberDto,
										@RequestBody PostUpdateRequestDto postUpdateRequestDto) {
		return ResponseEntity.ok(postService.updatePost(id,memberDto, postUpdateRequestDto));
	}

	// 게시글 삭제
	@DeleteMapping("/post/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id, @CurrentMember MemberDto memberDto){
		return ResponseEntity.ok(postService.deletePost(id, memberDto));
	}


}
