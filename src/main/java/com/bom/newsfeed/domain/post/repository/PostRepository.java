package com.bom.newsfeed.domain.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bom.newsfeed.domain.post.entity.Post;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

	List<Post> findAllByIdOrderByCreatedDateTimeDesc();

}