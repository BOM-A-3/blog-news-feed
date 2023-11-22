package com.bom.newsfeed.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.global.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDto memberDto = memberRepository.findByUsername(username)
			.map(MemberDto::from)
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다. " + username));

		return new UserDetailsImpl(memberDto);
	}
}