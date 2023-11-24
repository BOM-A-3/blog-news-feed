package com.bom.newsfeed.domain.like.service;

import static com.bom.newsfeed.global.common.dto.ResponseMessage.*;
import static com.bom.newsfeed.global.exception.ErrorCode.*;
import static com.bom.newsfeed.global.util.MemberUtil.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.like.entity.Likes;
import com.bom.newsfeed.domain.like.repository.LikeRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.service.PostService;
import com.bom.newsfeed.global.exception.ApiException;

@Service
public class LikeService {


	private final PostService postService;
	private final LikeRepository likeRepository;
	public LikeService(PostService postService, LikeRepository likeRepository) {
		this.postService = postService;
		this.likeRepository = likeRepository;
	}

	@Transactional
	public String addLike(Long postId, MemberDto memberDto) throws ApiException {

		Post post = postService.findPost(postId);

		// 자기 게시글에 좋아요 금지
		notMatchedMember(post.getMember().getUsername(),memberDto.getUsername());

		// 좋아요 정보를 추가하기 전에 전에 눌렀던 적이 있는지 체크
		Likes like = likeRepository.findByPostIdAndMemberId(postId, memberDto.getId());
		if(like == null) {
			like = new Likes(post, memberDto.toEntity());
		}
		else{
			throw new ApiException(ALREADY_EXIST_LIKE);
		}

		likeRepository.save(like);
		return LIKE;
	}

	// 해당 post에 본인이 누른 좋아요를 취소할 때 post 좋아요에 본인이름이 있는지 체크
	// 취소하는사람이 본인이 누른 좋아요 인지 체크
	public String deleteLike(Long postId, MemberDto memberDto) {
		// 선택한 게시글과 본인이 누른 좋아요가 있는지 체크
		Likes like = likeRepository.findByPostIdAndMemberId(postId, memberDto.getId());
		// 없으면 예외처리
		if(like == null) {
			throw new ApiException(NOT_INFO_MESSAGE);
		}
		else {
			likeRepository.delete(like);
			return DELETE_LIKE;
		}
	}


}
