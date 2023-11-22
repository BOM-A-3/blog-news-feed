package com.bom.newsfeed.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.PostFile;

import lombok.Getter;

@Getter
public class PostResponseDto {

	private Long id;

	private String title;

	private String content;

	private String memberName;

	private List<PostFile> postFileList;

	private LocalDateTime creatDateTime;

	private LocalDateTime modifyDateTime;

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.memberName = post.getMembers().getNickname();
		this.creatDateTime = post.getCreatedDateTime();
		this.modifyDateTime = post.getModifiedDateTime();
		this.postFileList = post.getPostFiles();
	}

}
