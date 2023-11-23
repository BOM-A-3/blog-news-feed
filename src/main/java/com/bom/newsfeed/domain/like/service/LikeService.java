package com.bom.newsfeed.domain.like.service;

import static com.bom.newsfeed.global.util.MemberUtil.*;

import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.like.dto.LikeResponseDto;
import com.bom.newsfeed.domain.like.entity.Like;
import com.bom.newsfeed.domain.like.repository.LikeRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.service.PostService;

@Service
public class LikeService {


	private final PostService postService;
	private final LikeRepository likeRepository;
	public LikeService(PostService postService, LikeRepository likeRepository) {
		this.postService = postService;
		this.likeRepository = likeRepository;
	}

	public LikeResponseDto addLike(Long postId, MemberDto memberDto) throws IllegalAccessException{
		Post post = postService.findPost(postId);
		notMatchedMember(post.getMember().getUsername(),memberDto.getUsername(),"본인 게시글에는 좋아요를 할수 없습니다.");
		Like like = new Like(post, memberDto.toEntity());
		likeRepository.save(like);
		return new LikeResponseDto();
	}

	// 해당 post에 본인이 누른 좋아요를 취소할 때 post 좋아요에 본인이름이 있는지 체크
	// 취소하는사람이 본인이 누른 좋아요 인지 체크
	public String deleteLike(Long postId, MemberDto memberDto) {
		Like like = likeRepository.findByPostIdAndMemberId(postId, memberDto.getId());
		if(like == null) {
			throw new NullPointerException("좋아요를 누른적이 없습니다.");
		}
		else {
			likeRepository.delete(like);
			return "해당하는" + postId + "번째 Post 좋아요를 취소했습니다.";
		}
	}

	public Long getPostTotalLike(Long postId){
		return likeRepository.countByPostId(postId);
	}

}
