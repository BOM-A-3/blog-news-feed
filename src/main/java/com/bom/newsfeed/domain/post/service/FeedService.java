package com.bom.newsfeed.domain.post.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.follow.entity.Follow;
import com.bom.newsfeed.domain.follow.repository.FollowRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.domain.post.dto.FeedResponse;
import com.bom.newsfeed.domain.post.dto.GetAllPostResponseDto;
import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.FeedRepository;
import com.bom.newsfeed.domain.post.repository.PostRepository;
import com.bom.newsfeed.global.common.constant.ErrorCode;
import com.bom.newsfeed.global.exception.ApiException;
import com.bom.newsfeed.global.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedService {

	private final FeedRepository feedRepository;
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final FollowRepository followRepository;

	// 전체 조회
	@Transactional
	public FeedResponse searchFeed(MemberDto memberDto, Long cursorId, SearchFeedCondition condition) {

		Optional<Member> member = Optional.empty();
		if (memberDto != null) {
			member = Optional.of(memberRepository.findById(memberDto.getId())
				.orElseThrow(MemberNotFoundException::new));
		}

		Post cusorPost = null;
		if (cursorId != null && cursorId != 0) {
			cusorPost = postRepository.findById(cursorId)
				.orElseThrow(() -> new ApiException(ErrorCode.INVALID_VALUE));
		}

		List<Post> result = feedRepository.searchFeed(cusorPost, condition);
		if (result.isEmpty()) {
			return null;
		}
		LinkedList<Post> posts = new LinkedList<>(result);
		Long lastCursorId = posts.getLast().getId();

		Set<Post> feeds = rankProcess(posts, member);
		List<GetAllPostResponseDto> feedResponses = feeds.stream()
			.map(GetAllPostResponseDto::new)
			.toList();

		return FeedResponse.from(feedResponses, lastCursorId);

	}

	//페이징한 목록에서 랭킹 나누기
	//1. 페이징-> 최신글 id 로 처음 나감
	//2. 좋아요 많은 순으로 정렬
	//3. 리스트의 처음과 끝 id 사이 중(날짜로찾기) 친구글이 있는 경우 맨위로
	// => 이경우 마지막 cursorId가 뭔지모름 -> response에서 따로 내려주기


	public Set<Post> rankProcess(List<Post> result, Optional<Member> member) {
		LocalDateTime lastCursorCreatedDateTime = ((LinkedList<Post>)result).getLast().getCreatedDateTime();
		LinkedHashSet<Post> resultSet = new LinkedHashSet<>();

		//좋아요 순 정렬
		result.sort(this::compareLikeCount);

		//로그인 안한 경우
		if (member.isEmpty()) {
			resultSet.addAll(result);
			return resultSet;
		}

		//로그인 한 경우
		//팔로우 한 회원목록 가져오기
		List<Follow> follows = followRepository.findAllByFollowingId(member.get().getId());
		if (follows.isEmpty()) {
			resultSet.addAll(result);
			return resultSet;

		}

		//팔로우 한 회원의 게시글 add 먼저하기 -> Set 자료구조를 활용하여 중복 add 방지
		for (Post post : result) {
			for (Follow follow : follows) {
				List<Post> followingPost =
					postRepository.findAllByMember_IdAndCreatedDateTimeAfterOrderByCreatedDateTime(
						follow.getFollowerId(), lastCursorCreatedDateTime);

				resultSet.addAll(followingPost);
				resultSet.add(post);
			}
		}
		return resultSet;
	}

	private int compareLikeCount(Post o1, Post o2) {
		Long o1LikeCount = (long)o1.getLikes().size();
		Long o2LikeCount = (long)o2.getLikes().size();
		return o2LikeCount.compareTo(o1LikeCount);
	}
}
