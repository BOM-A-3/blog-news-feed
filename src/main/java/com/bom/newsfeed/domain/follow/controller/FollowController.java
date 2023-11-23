package com.bom.newsfeed.domain.follow.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.follow.service.FollowService;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.global.annotation.CurrentMember;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "팔로우 API", description = "팔로우 API 컨트롤러")
@RequestMapping("/api/follow")
@RestController
public class FollowController {

	private final FollowService followService;

	public FollowController(FollowService followService) {
		this.followService = followService;
	}

	//팔로우 등록
	@PostMapping("/{followingId}")
	public void follow(
		@PathVariable(name = "followingId") Long followingId,
		@CurrentMember MemberDto memberDto
	) {
		followService.follow(followingId, memberDto);
	}

	//팔로우 취소
	@DeleteMapping("/{followingId}")
	public void unFollow(
		@PathVariable(name = "followingId") Long followingId,
		@CurrentMember MemberDto memberDto
	) {
		followService.unFollow(followingId, memberDto);

	}
}
