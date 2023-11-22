package com.bom.newsfeed.global.auth;

import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.global.exception.MemberNotFoundException;
import com.bom.newsfeed.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsService {

	private final MemberRepository memberRepository;

	public UserDetailsImpl getUserDetails(String username) {
		MemberDto memberDto = memberRepository.findByUsername(username)
			.map(MemberDto::from)
			.orElseThrow(MemberNotFoundException::new);
		return new UserDetailsImpl(memberDto);
	}
}