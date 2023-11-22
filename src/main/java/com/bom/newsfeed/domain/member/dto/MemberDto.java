package com.bom.newsfeed.domain.member.dto;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDto {
	private final String username;
	private final String password;
	private final String nickname;
	private final MemberRole role;

	public MemberDto(String username, String password, String nickname, MemberRole role) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
	}

	public static MemberDto from (Member entity) {
		return new MemberDto(
			entity.getUsername(),
			entity.getPassword(),
			entity.getNickname(),
			entity.getRole()
		);
	}
}
