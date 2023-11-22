package com.bom.newsfeed.domain.postfile.dto;

import com.bom.newsfeed.domain.postfile.entity.FileType;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostFileRequestDto {
	@NotBlank
	private String url;

	private FileType fileType;

}
