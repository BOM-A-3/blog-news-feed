package com.bom.newsfeed.domain.follow.service;

import static com.bom.newsfeed.global.common.constant.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.follow.entity.Follow;
import com.bom.newsfeed.domain.follow.entity.FollowPk;
import com.bom.newsfeed.domain.follow.repository.FollowRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.global.exception.AlreadyExistFollowingException;
import com.bom.newsfeed.global.exception.ApiException;
import com.bom.newsfeed.global.exception.FollowingNotFoundException;
import com.bom.newsfeed.global.exception.MemberNotFoundException;

@Service
public class FollowService {
	private final FollowRepository followRepository;
	private final MemberRepository memberRepository;

	public FollowService(FollowRepository followRepository, MemberRepository memberRepository) {
		this.followRepository = followRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void follow(Long followerId, MemberDto following) {
		//팔로잉할 회원정보가 존재하는지 확인
		if (!memberRepository.existsById(followerId)) {
			throw new MemberNotFoundException();
		}

		//자기자신을 팔로우하는건지 확인
		if(followerId.equals(following.getId())) {
			throw new ApiException(INVALID_VALUE);
		}

		//이미 팔로잉 했는지 확인
		FollowPk id = FollowPk.of(followerId, following.getId());
		if (followRepository.existsById(id)) {
			throw new AlreadyExistFollowingException();
		}

		Follow follow = Follow.of(id.getFollowerId(), id.getFollowingId());
		followRepository.save(follow);
	}

	@Transactional
	public void unFollow(Long followerId, MemberDto following) {
		//팔로잉 취소 할 회원정보가 존재하는지 확인
		if (!memberRepository.existsById(followerId)) {
			throw new MemberNotFoundException();
		}

		//Follow 찾기
		FollowPk id = FollowPk.of(followerId, following.getId());
		Follow follow = followRepository.findById(id)
			.orElseThrow(FollowingNotFoundException::new);

		followRepository.delete(follow);
	}
}
