package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
	@NotBlank
	private String title;

	private String content;

	private List<String> fileUrl;

	@NotBlank
	private String category;
}