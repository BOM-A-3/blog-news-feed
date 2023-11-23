package com.bom.newsfeed.domain.post.dto;

import com.bom.newsfeed.domain.postfile.entity.FileType;
import com.bom.newsfeed.domain.postfile.entity.PostFile;

import lombok.Getter;

@Getter
public class GetPostFIleResponseDto {

	private final String url;

	private final FileType filetype;

	public GetPostFIleResponseDto(PostFile postFile) {
		this.url = postFile.getUrl();
		this.filetype = postFile.getFiletype();
	}


}
