package com.bom.newsfeed.domain.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bom.newsfeed.domain.follow.entity.Follow;
import com.bom.newsfeed.domain.follow.entity.FollowPk;

public interface FollowRepository extends JpaRepository<Follow, FollowPk> {
	List<Follow> findAllByFollowingId(Long followerId);

}
