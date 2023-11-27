package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
	@NotBlank
	private String title;

	private String content;

	@NotBlank
	private String category;

	private List<String> fileUrl;


}
