package com.bom.newsfeed.domain.post.dto;

import com.bom.newsfeed.domain.postfile.entity.PostFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {
	@NotBlank
	private String title;

	private String content;

	private String url;

	private PostFile postFile;
}
