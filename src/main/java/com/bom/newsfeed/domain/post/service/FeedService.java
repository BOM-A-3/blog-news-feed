package com.bom.newsfeed.domain.post.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.follow.entity.Follow;
import com.bom.newsfeed.domain.follow.repository.FollowRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.member.repository.MemberRepository;
import com.bom.newsfeed.domain.post.dto.FeedResponse;
import com.bom.newsfeed.domain.post.dto.GetPostAllResponseDto;
import com.bom.newsfeed.domain.post.dto.SearchFeedCondition;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.FeedRepository;
import com.bom.newsfeed.domain.post.repository.PostRepository;
import com.bom.newsfeed.global.common.constant.ErrorCode;
import com.bom.newsfeed.global.exception.ApiException;
import com.bom.newsfeed.global.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
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

		List<Post> feeds = rankProcess(posts, member);
		List<GetPostAllResponseDto> feedResponses = feeds.stream().map(GetPostAllResponseDto::new)
				.toList();

		return FeedResponse.from(feedResponses, lastCursorId);

	}

	//페이징한 목록에서 랭킹 나누기
	//1. 페이징-> 최신글 id 로 처음 나감
	//2. 좋아요 많은 순으로 정렬
	//3. 리스트의 처음과 끝 id 사이 중(날짜로찾기) 친구글이 있는 경우 맨위로
	// => 이경우 마지막 cursorId가 뭔지모름 -> response에서 따로 내려주기
	public List<Post> rankProcess(List<Post> result, Optional<Member> member) {
		LocalDateTime lastCursorCreatedDateTime = ((LinkedList<Post>) result).getLast().getCreatedDateTime();
		result.sort(this::compareLikeCount);
		if (member.isPresent()) {
			List<Follow> follows = followRepository.findFollowsByFollowingId(member.get().getId());
			result.forEach(
				post -> {
					int idxCount = 0;
					for (Follow follow : follows) {
						//following 게시글중 최신글을 가장 앞에두기 위해 idx를 통해 넣는 곳 제어
						List<Post> followingPost = postRepository.findAllByMember_IdAndCreatedDateTimeBefore(
							follow.getFollowingId(), lastCursorCreatedDateTime);
						if (post.equals(followingPost)) {
							result.remove(post);
							result.add(idxCount++, post);
						}
					}
				});
		}

		return result;
	}

	private int compareLikeCount(Post o1, Post o2) {
		Long o1LikeCount = (long)o1.getLikes().size();
		Long o2LikeCount = (long)o2.getLikes().size();
		return o1LikeCount.compareTo(o2LikeCount);
	}
}
