package com.bom.newsfeed.domain.post.repository;

import static com.bom.newsfeed.domain.post.entity.QPost.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.entity.Post;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class FeedRepositoryCustomImpl implements FeedRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;

	public FeedRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public List<Post> searchFeed(Post cursorPost, SearchFeedCondition condition) {
		return jpaQueryFactory.selectFrom(post)
			.where(
				categoryTypeFilter(condition.getCategoryType()),
				cursorPagination(cursorPost)
			)
			.limit(condition.getSize())
			.orderBy(new OrderSpecifier<>(Order.DESC, post.createdDateTime))
			.fetch();

	}

	private Predicate categoryTypeFilter(CategoryType categoryType) {
		if (categoryType == null) {
			return null;
		}
		return post.category.category.eq(categoryType);
	}

	private Predicate cursorPagination(Post cursorPost) {

		if(cursorPost == null) {
			return null;
		}
		return post.id.lt(cursorPost.getId());
	}
}
