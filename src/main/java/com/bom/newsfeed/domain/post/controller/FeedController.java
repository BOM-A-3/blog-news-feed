package com.bom.newsfeed.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.service.FeedService;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.constant.ResponseCode;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

@RequestMapping("/api/feed")
@RestController
public class FeedController {
	private final FeedService feedService;

	public FeedController(FeedService feedService) {
		this.feedService = feedService;
	}

	@GetMapping
	public ResponseEntity searchFeed(
		@CurrentMember MemberDto memberDto,
		@RequestParam(value = "cursor") Long cursorId,
		@RequestParam(value = "size") Long size,
		@RequestParam(value = "category", required = false) String category
	) {

		CategoryType categoryType = CategoryType.findByName(category);
		SearchFeedCondition condition = SearchFeedCondition.of(size, categoryType);
		return ResponseEntity.ok(
			SuccessResponse.builder()
				.responseCode(ResponseCode.GET_FEED)
				.data(feedService.searchFeed(memberDto, cursorId, condition))
				.build()
		);
	}

	@GetMapping("member/{memberId}")
	public void searchMemberFeed(
		@CurrentMember MemberDto memberDto,
		@PathVariable(name = "memberId") Long memberId,
		@RequestParam(value = "cursor") Long cursorId,
		@RequestParam(value = "size") Long size
	) {}
}
