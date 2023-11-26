package com.bom.newsfeed.domain.postfile.controller;

import static com.bom.newsfeed.global.common.constant.ResponseCode.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.postfile.service.PostFileService;
import com.bom.newsfeed.global.annotation.CurrentMember;
import com.bom.newsfeed.global.common.dto.ErrorResponse;
import com.bom.newsfeed.global.common.dto.SuccessResponse;
import com.bom.newsfeed.global.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "파일 API", description = "파일 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

	private final PostFileService postFileService;


	@Operation(summary = "파일 삭제", description = "파일 삭제 API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "파일 삭제 완료",
			content = @Content(schema = @Schema(implementation = SuccessResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "삭제 요청한 파일이 없을때",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@DeleteMapping("/post/{postId}/file/{fileId}")
	public ResponseEntity<SuccessResponse<Object>> deleteFile(@PathVariable Long postId,
										@PathVariable Long fileId,
										@CurrentMember MemberDto memberDto)  {
		postFileService.deleteFile(postId,fileId,memberDto);
		return  ResponseEntity.ok(SuccessResponse.builder().responseCode(DELETE_FILE).build());
	}

}
