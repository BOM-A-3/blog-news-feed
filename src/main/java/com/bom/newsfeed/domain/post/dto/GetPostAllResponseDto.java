package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import com.bom.newsfeed.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class GetPostAllResponseDto {

	private final String title;

	private final String content;

	private final String memberName;

	private final List<GetPostFIleResponseDto> postFiles;

	public GetPostAllResponseDto(Post post)
	{
		this.title = post.getTitle();
		this.content = post.getContent();
		this.memberName = post.getMember().getNickname();
		this.postFiles = post.getPostFiles().stream().map(GetPostFIleResponseDto::new).toList();
	}

}
