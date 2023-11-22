package com.bom.newsfeed.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.dto.request.SignupRequest;
import com.bom.newsfeed.domain.member.dto.request.UpdateProfileRequest;
import com.bom.newsfeed.domain.member.dto.response.UpdateProfileResponse;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.global.exception.AccessDeniedException;
import com.bom.newsfeed.global.exception.AlreadyExistMemberException;
import com.bom.newsfeed.global.exception.AlreadyExistNicknameException;
import com.bom.newsfeed.global.exception.ChangePasswordMissMatchException;
import com.bom.newsfeed.global.exception.MemberNotFoundException;

@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private static final String ACCESS_DENIED_MESSAGE = "회원 정보에 대한 권한이 없습니다.";

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

	public UpdateProfileResponse updateProfile(Long memberId, UpdateProfileRequest request, MemberDto memberDto) {
		//회원 존재 체크
		Member member = memberRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);
		//권환 체크
		validateMember(memberDto.getUsername(), member.getUsername());

		// 프로필 업데이트 검증
		//닉네임 중복검사
		verifyNickname(request.getNickname());
		//비밀번호/ 재입력 검증
		if (!request.getChangePassword().equals(request.getConfirmPassword())) {
			throw new ChangePasswordMissMatchException();
		}

		//프로필 수정
		member.updateProfile(request);

		return UpdateProfileResponse.from(member);
	}

	private void validateMember(String currentUsername, String targetUsername) {
		if (!currentUsername.equals(targetUsername)) {
			throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
		}
	}
}
