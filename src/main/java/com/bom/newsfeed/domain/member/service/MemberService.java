package com.bom.newsfeed.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.member.dto.request.SignupRequest;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.global.exception.AlreadyExistMemberException;
import com.bom.newsfeed.global.exception.AlreadyExistNicknameException;

@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void signup(SignupRequest request) {
		if (memberRepository.existsByUsername(request.getUsername())) {
			throw new AlreadyExistMemberException();
		}

		verifyNickname(request.getNickname());

		memberRepository.save(request.toEntity(passwordEncoder));
	}

	public void verifyNickname(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new AlreadyExistNicknameException();
		}
	}
}
