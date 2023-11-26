package com.bom.newsfeed.domain.post.dto;

import com.bom.newsfeed.domain.category.constant.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchFeedCondition {
	private final Long size;
	private final CategoryType categoryType;
	public static SearchFeedCondition of(Long size, CategoryType categoryType) {
		return new SearchFeedCondition(size, categoryType);
	}
}
