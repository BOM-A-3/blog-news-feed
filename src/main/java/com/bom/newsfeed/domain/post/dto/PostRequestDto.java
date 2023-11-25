package com.bom.newsfeed.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
	@NotBlank
	private String title;

	private String content;

	@NotBlank
	private String category;

	public PostRequestDto(PostUpdateRequestDto postUpdateRequestDto){
		this.title = postUpdateRequestDto.getTitle();
		this.content = postUpdateRequestDto.getContent();
		this.category = postUpdateRequestDto.getCategory();
	}


}
