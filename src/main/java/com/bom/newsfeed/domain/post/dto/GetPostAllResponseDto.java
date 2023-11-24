package com.bom.newsfeed.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bom.newsfeed.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class GetPostAllResponseDto {

	private final Long id;

	private final String title;

	private final String content;

	private final String memberName;

	private final List<GetPostFIleResponseDto> postFiles;

	private final LocalDateTime createDateTime;

	private final long likes;

	public GetPostAllResponseDto(Post post)
	{
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.memberName = post.getMember().getNickname();
		this.postFiles = post.getPostFiles().stream().map(GetPostFIleResponseDto::new).toList();
		this.createDateTime = post.getCreatedDateTime();
		this.likes = post.getLikes().size();
	}

}
