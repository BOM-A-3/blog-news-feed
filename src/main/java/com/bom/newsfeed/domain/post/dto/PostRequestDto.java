package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.category.entity.Category;
import com.bom.newsfeed.domain.postfile.entity.PostFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {
	@NotBlank
	private String title;

	private String content;

	private List<MultipartFile> multipartFiles;

	@NotBlank
	private String category;
}
