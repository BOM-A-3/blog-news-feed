package com.bom.newsfeed.domain.member.dto;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDto {
	private final Long id;
	private final String username;
	private final String password;
	private final String nickname;
	private final MemberRole role;

	public MemberDto(Long id, String username, String password, String nickname, MemberRole role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
	}

	public static MemberDto from (Member entity) {
		return new MemberDto(
			entity.getId(),
			entity.getUsername(),
			entity.getPassword(),
			entity.getNickname(),
			entity.getRole()
		);
	}

	public Member toEntity() {
		return Member.of(
			id,
			username,
			password,
			nickname,
			role
		);
	}
}
