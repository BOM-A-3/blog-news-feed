package com.bom.newsfeed.domain.post.controller;

import static com.bom.newsfeed.global.common.constant.ResponseCode.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.post.dto.PostUpdateRequestDto;
import com.bom.newsfeed.domain.post.service.PostService;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "게시글 API", description = "게시글 API")
@RequestMapping("/api")
@RestController
@Slf4j
public class PostController {

	private final PostService postService;


	public PostController(PostService postService) {
		this.postService = postService;
	}

	// 게시글 생성
	@Operation(summary = "포스트 생성", description = "포스트 생성 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "포스트 생성 완료",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "포스트 생성 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	// @Parameter(name = "files", description = "업로드파일", content = @Content(mediaType = "multipart/form-data"))
	@PostMapping(path = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public ResponseEntity<SuccessResponse<Object>> createPost(
		@Parameter(name = "postRequestDto", description = "게시글 파라미터", content = @Content(mediaType = "application/json"))
		@Valid @RequestPart(value = "postRequestDto") PostRequestDto postRequestDto,
		@Parameter(name = "files", description = "업로드파일", content = @Content(mediaType = "multipart/form-data"))
		@RequestPart(required = false) List<MultipartFile> files,
		@CurrentMember MemberDto memberDto){

		 return ResponseEntity.status(CREATE_POST.getHttpStatus().value()).body(
			 SuccessResponse.builder()
				 .responseCode(CREATE_POST)
				 .data( postService.createPost(postRequestDto, memberDto, files))
				 .build()
			 );

	}

	@Operation(summary = "post 목록 조회" , description = "포스트 목록 조회 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "목록 조회 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "목록이 없는 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	// 게시글 조회
	@GetMapping("/post")
	public ResponseEntity<SuccessResponse<Object>> getAllPost() {
		return ResponseEntity.status(GET_POST.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(GET_POST)
				.data(postService.getAllPost())
				.build()
		);
	}

	// 게시글 선택조회
	@Operation(summary = "post 선택 조회" , description = "포스트 선택 조회 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "목록 조회 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "목록 조회 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@GetMapping("/post/{id}")
	public ResponseEntity<SuccessResponse<Object>> selectPost(@PathVariable Long id){

		return ResponseEntity.status(GET_SELECT_POST.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(GET_SELECT_POST)
				.data(postService.selectPost(id))
				.build()
		);
	}

	// 게시글 수정
	@Operation(summary = "post update" , description = "post update API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "update 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "update 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "필수 조건 입력 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PutMapping(path = "/post/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public ResponseEntity<SuccessResponse<Object>> updatePost(@PathVariable Long id,
		@Parameter(name = "postRequestDto", description = "게시글 파라미터", content = @Content(mediaType = "application/json"))
		@Valid @RequestPart(value = "postUpdateRequestDto") PostUpdateRequestDto postUpdateRequestDto,
		@Parameter(name = "files", description = "업로드파일", content = @Content(mediaType = "multipart/form-data"))
		@RequestPart(required = false)  List<MultipartFile> updateFile,
		@CurrentMember MemberDto memberDto){
		return ResponseEntity.status(POST_UPDATE.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(POST_UPDATE)
				.data(postService.updatePost(id,memberDto, postUpdateRequestDto, updateFile))
				.build()
		);
	}

	// 게시글 삭제
	@Operation(summary = "post delete" , description = "post delete API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "delete 성공",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "delete 실패",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@DeleteMapping("/post/{id}")
	public ResponseEntity<SuccessResponse<Object>> deletePost(@PathVariable Long id, @CurrentMember MemberDto memberDto){
		return ResponseEntity.status(POST_DELETE.getHttpStatus().value()).body(
			SuccessResponse.builder()
				.responseCode(POST_DELETE)
				.data(postService.deletePost(id, memberDto))
				.build()
		);
	}


}
