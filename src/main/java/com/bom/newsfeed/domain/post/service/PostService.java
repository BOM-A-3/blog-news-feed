package com.bom.newsfeed.domain.post.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.dto.PostResponseDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public PostResponseDto createPost(PostRequestDto postRequestDto, Member member) {
		Post post = new Post(postRequestDto, member);

		Post savePost = postRepository.save(post);
		return new PostResponseDto(savePost);
	}












	private Post findPost(Long id) {
		return postRepository.findById(id).orElseThrow(()->
			 new IllegalArgumentException("정보를 찾을 수 없습니다.")
		);
	}


}



