package com.bom.newsfeed.domain.post.service;

import java.security.PublicKey;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.category.entity.Category;
import com.bom.newsfeed.domain.category.repository.CategoryRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.GetPostAllResponseDto;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.dto.PostResponseDto;
import com.bom.newsfeed.domain.post.dto.SelectPostResponseDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;

	public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
	}


	@Transactional
	public PostResponseDto createPost(PostRequestDto postRequestDto, MemberDto member) {

		Category category = categoryRepository.findByCategory(CategoryType.getType(postRequestDto.getCategory()));
		if(category == null) {
			category  = new Category(CategoryType.getType(postRequestDto.getCategory()));
			categoryRepository.save(category);
		}
		Post post = new Post(postRequestDto, member.toEntity(), category);
		//파일 추가
		//

		Post savePost = postRepository.save(post);
		return new PostResponseDto(savePost);
	}

	// 전체 조회
	@Transactional(readOnly = true)
	public List<GetPostAllResponseDto> getAllPost() {
		List<Post> postList = postRepository.findAllByOrderByCreatedDateTimeDesc();
		if (postList.isEmpty()) {
			throw new NullPointerException("목록이 비어있습니다");
		} else {
			return postList.stream().map(GetPostAllResponseDto::new).toList();
		}
	}

	// 선택 조회
	@Transactional(readOnly = true)
	public SelectPostResponseDto selectPost(Long id){
		Post post = findPost(id);
		return new SelectPostResponseDto(post);
	}

	// 게시글 업데이트
	@Transactional
	public PostResponseDto updatePost(Long id, MemberDto member , PostRequestDto postRequestDto) throws IllegalAccessException {
		Post post = findPost(id);
		Category category = categoryRepository.findByCategory(CategoryType.getType(postRequestDto.getCategory()));
		matchedMember(post,member.toEntity());

		post.update(postRequestDto, category);

		return new PostResponseDto(post);
	}

	@Transactional
	public Long deletePost(Long id, MemberDto memberDto) throws IllegalAccessException
	{
		Post post = findPost(id);
		matchedMember(post, memberDto.toEntity());
		postRepository.delete(post);
		return id;
	}





	private void matchedMember(Post post, Member member) throws IllegalAccessException {
		if(!post.getMember().getUsername().equals(member.getUsername()))
			throw new IllegalAccessException("게시글 작성자가 아닙니다.");
	}


	private Post findPost(Long id) {
		return postRepository.findById(id).orElseThrow(()->
			 new IllegalArgumentException("정보를 찾을 수 없습니다.")
		);
	}


}



