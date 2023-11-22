package com.bom.newsfeed.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {
	@NotBlank
	private String title;

	private String content;
}
