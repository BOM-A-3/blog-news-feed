package com.bom.newsfeed.domain.follow.entity;

import java.io.Serializable;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class FollowPk implements Serializable {
	private Long followerId;
	private Long followingId;

	public static FollowPk of(Long followerId, Long followingId) {
		return new FollowPk(followerId, followingId);
	}
}
