package com.bom.newsfeed.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
	private final Long id;

	@NotBlank
	@Size(max = 1000, message = "최대 1000자까지 입력")
	private final String comment;
}
