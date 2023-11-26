package com.bom.newsfeed.domain.post.repository;

import java.util.List;

import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.entity.Post;

public interface FeedRepositoryCustom {
	List<Post> searchFeed(Post cursorPost, SearchFeedCondition condition);
}
