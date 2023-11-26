package com.bom.newsfeed.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bom.newsfeed.domain.post.entity.Post;

public interface FeedRepository extends JpaRepository<Post, Long>, FeedRepositoryCustom{
}
