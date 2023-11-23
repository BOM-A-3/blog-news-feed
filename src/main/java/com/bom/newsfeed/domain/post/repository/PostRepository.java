package com.bom.newsfeed.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bom.newsfeed.domain.post.entity.Post;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

	List<Post> findAllByOrderByCreatedDateTimeDesc();

	// Like findByLik

	// @Query("select count(l.id) from Like l")
	// Long getTotalCount(Long PostId);

}
