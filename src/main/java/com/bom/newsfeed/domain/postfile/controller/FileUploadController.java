package com.bom.newsfeed.domain.postfile.controller;

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
import com.bom.newsfeed.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

	private final AmazonS3Client amazonS3Client;

	// @Value("${cloud.aws.s3.bucket}")
	// private String bucket;
	//
	// @PostMapping("/upload")
	// public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFilefile) {
	// 	try {
	// 		StringfileName=file.getOriginalFilename();
	// 		StringfileUrl= "https://" + bucket + "/test" +fileName;
	// 		ObjectMetadatametadata= new ObjectMetadata();
	// 		metadata.setContentType(file.getContentType());
	// 		metadata.setContentLength(file.getSize());
	// 		amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
	// 		return ResponseEntity.ok(fileUrl);
	// 	} catch (IOExceptione) {
	// 		e.printStackTrace();
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// 	}
	// }

	// Controller.java

	private final PostFileService postFileService;
	// /**
	//  * 그룹(팀) 생성
	//  * @param name
	//  * @param file
	//  * @return
	//  */
	// @PostMapping(path = "/file", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	// public ResponseEntity createFile(@RequestPart(value = "name") String name,
	// 								 @RequestPart(value = "file", required = false) MultipartFile file
	// ){
	// 	// postFileService.createFile(name, file);
	// 	return new ResponseEntity(null, HttpStatus.OK);
	// }

	@DeleteMapping("/post/{postId}/file/{fileId}")
	public ResponseEntity<?> deleteFile(@PathVariable Long postId,
										@PathVariable Long fileId,
										@CurrentMember MemberDto memberDto) throws Exception {
		postFileService.deleteFile(postId,fileId,memberDto);
		return  ResponseEntity.ok("삭제 완료");
	}





}
