package com.bom.newsfeed.domain.follow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(FollowPk.class)
@Table(name = "follow")
@Entity
public class Follow {
	@Id
	@JoinColumn(name = "member_id")
	private Long followerId; //팔로워
	@Id
	@JoinColumn(name = "member_id")
	private Long followingId; //팔로잉

	public Follow(Long followerId, Long followingId) {
		this.followerId = followerId;
		this.followingId = followingId;
	}


	public static Follow of(Long followerId, Long followingId) {
		return new Follow(followerId, followingId);
	}
}
