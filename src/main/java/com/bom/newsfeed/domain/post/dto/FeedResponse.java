package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedResponse {
	private final List<GetAllPostResponseDto> feeds;
	private final Long lastCursorId;


	public static FeedResponse from(List<GetAllPostResponseDto> feeds, Long lastCursorId) {
		return new FeedResponse(feeds, lastCursorId);
	}
}
