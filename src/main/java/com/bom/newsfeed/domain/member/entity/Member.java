package com.bom.newsfeed.domain.member.entity;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.domain.member.dto.request.UpdateProfileRequest;
import com.bom.newsfeed.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column(
		name = "username",
		nullable = false,
		unique = true,
		length = 100
	)
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nickname", length = 100)
	private String nickname;

	@Column(name = "introduce", length = 300)
	private String introduce;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private MemberRole role;

	private Member(String username, String password, String nickname, MemberRole role) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.createBy = username;
		this.modifiedBy = username;
	}

	public static Member of(String username, String password, String nickname) {
		return new Member(username, password, nickname, MemberRole.USER);
	}

	public void updateProfile(UpdateProfileRequest request) {
		this.password = request.getChangePassword();
		this.nickname = request.getNickname();
		this.introduce = request.getIntroduce();
	}
}
