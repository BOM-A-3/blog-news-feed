package com.bom.newsfeed.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bom.newsfeed.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like,Long> {

	@Query("select count(l) from Like l WHERE l.post.id = : postId")
	Long totalLikeCount(Long postId);

	// postId와 MemberId가 일치하는 Like 가져오기
	Like findByPostIdAndMemberId(Long postId, Long memberId);

	// 해당 포스트의 Like개수 가져오기
	Long countByPostId(Long postId);
}
