package com.bom.newsfeed.domain.post.service;

import static com.bom.newsfeed.global.util.MemberUtil.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.category.constant.CategoryType;
import com.bom.newsfeed.domain.category.entity.Category;
import com.bom.newsfeed.domain.category.repository.CategoryRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.dto.GetAllPostResponseDto;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.dto.PostResponseDto;
import com.bom.newsfeed.domain.post.dto.PostUpdateRequestDto;
import com.bom.newsfeed.domain.post.dto.SelectPostResponseDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;
import com.bom.newsfeed.domain.postfile.service.PostFileService;
import com.bom.newsfeed.global.exception.NotFoundInfoException;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;

	private final PostFileService postFileService;
	public PostService(PostRepository postRepository, CategoryRepository categoryRepository,
		PostFileService postFileService) {
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.postFileService = postFileService;
	}



	@Transactional
	public PostResponseDto createPost(PostRequestDto postRequestDto, MemberDto member, List<MultipartFile> files) {

		Category category = categoryRepository.findByCategory(CategoryType.getType(postRequestDto.getCategory()));
		if(category == null) { // 입력 받은 카테고리가 DB에 없으면 카테고리 생성
			category  = new Category(CategoryType.getType(postRequestDto.getCategory()));
			categoryRepository.save(category);
		}
		Post post = new Post(postRequestDto, member.toEntity(), category); // 포스트 객체 정보 입력


		if(files != null) {
			post = postFileService.createFile(files, post); // 입력받은 파일들을 저장
		}

		Post savePost = postRepository.save(post); // 포스트 저장
		return new PostResponseDto(savePost);
	}

	// 전체 조회
	@Transactional(readOnly = true)
	public List<GetAllPostResponseDto> getAllPost() {
		List<Post> postList = postRepository.findAllByOrderByCreatedDateTimeDesc(); // post에 저장돼 있는 정보
		if (postList.isEmpty()) {
			throw new NotFoundInfoException();
		}
		return postList.stream().map(GetAllPostResponseDto::new).toList();

	}

	// 선택 조회
	@Transactional(readOnly = true)
	public SelectPostResponseDto selectPost(Long id){
		Post post = findPost(id);

		return new SelectPostResponseDto(post);

	}

	// 게시글 업데이트
	@Transactional
	public PostResponseDto updatePost(Long postId, MemberDto member ,
									  PostUpdateRequestDto postUpdateRequestDto,
		 							  List<MultipartFile> updateFile){

		Post post = findPost(postId);
		matchedMember(post.getMember().getUsername(),member.getUsername()); // 유저 정보가 일치한지 확인

		// 파일 업데이트
		post = postFileService.updateFile(post, postUpdateRequestDto.getFileUrl()); //받은 URl 정보와 일치하지 않은 파일은 삭제
		post = postFileService.createFile(updateFile, post); // 추가로 입력받은 파일 생성

		// 카테고리 업데이트
		Category category = categoryRepository.findByCategory(CategoryType.getType(postUpdateRequestDto.getCategory()));
		if(category == null) {
			category  = new Category(CategoryType.getType(postUpdateRequestDto.getCategory()));
			categoryRepository.save(category);
		}

		PostRequestDto postRequestDto = new PostRequestDto(postUpdateRequestDto);
		post.update(postRequestDto, category);

		return new PostResponseDto(post);
	}


	// 게시글 삭제
	@Transactional
	public Long deletePost(Long id, MemberDto memberDto)
	{
		Post post = findPost(id);
		matchedMember(post.getMember().getUsername(),memberDto.getUsername());
		postRepository.delete(post);
		return id;
	}

	// 게시글 탐색
	public Post findPost(Long id){
		return postRepository.findById(id).orElseThrow(NotFoundInfoException::new
		);
	}

}



