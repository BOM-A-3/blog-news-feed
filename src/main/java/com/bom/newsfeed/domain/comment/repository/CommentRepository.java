package com.bom.newsfeed.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bom.newsfeed.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPostIdOrderByCreatedDateTimeDesc(Long postId);
}
