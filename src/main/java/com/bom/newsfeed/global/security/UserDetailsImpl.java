package com.bom.newsfeed.global.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bom.newsfeed.domain.member.constant.MemberRole;
import com.bom.newsfeed.domain.member.dto.MemberDto;

public class UserDetailsImpl implements UserDetails {

	private final MemberDto memberDto;

	public UserDetailsImpl(MemberDto memberDto) {
		this.memberDto = memberDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		MemberRole role = memberDto.getRole();
		String authority = role.getAuthority();

		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);

		return authorities;
	}

	@Override
	public String getUsername() {
		return memberDto.getUsername();
	}
	@Override
	public String getPassword() {
		return memberDto.getPassword();
	}

	public MemberDto getMemberDto() {
		return memberDto;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}