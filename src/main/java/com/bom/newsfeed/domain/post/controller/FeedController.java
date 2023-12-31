package com.bom.newsfeed.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.service.FeedService;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.constant.ResponseCode;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "피드 API", description = "파일 API")
@RequestMapping("/api/feed")
@RestController
public class FeedController {
	private final FeedService feedService;

	public FeedController(FeedService feedService) {
		this.feedService = feedService;
	}

	@Operation(summary = "피드 조회", description = "피드 조회 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "피드 조회 API",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "로그인한 유저의 회원정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "커서 대상 게시글 정보가 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@GetMapping
	public ResponseEntity searchFeed(
		@CurrentMember MemberDto memberDto,
		@RequestParam(value = "cursor") Long cursorId,
		@RequestParam(value = "size") Long size,
		@RequestParam(value = "category", required = false) String category
	) {

		CategoryType categoryType = CategoryType.getType(category);
		SearchFeedCondition condition = SearchFeedCondition.of(size, categoryType);
		return ResponseEntity.ok(
			SuccessResponse.builder()
				.responseCode(ResponseCode.GET_FEED)
				.data(feedService.searchFeed(memberDto, cursorId, condition))
				.build()
		);
	}
}
