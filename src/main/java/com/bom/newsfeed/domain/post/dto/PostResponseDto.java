package com.bom.newsfeed.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bom.newsfeed.domain.category.entity.Category;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.PostFile;

import lombok.Getter;

@Getter
public class PostResponseDto {

	private final Long id;

	private final String title;

	private final String content;

	private final String memberName;

	private final List<PostFile> postFileList;

	private final LocalDateTime creatDateTime;

	private final LocalDateTime modifyDateTime;

	private final Category category;

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.memberName = post.getMembers().getNickname();
		this.creatDateTime = post.getCreatedDateTime();
		this.modifyDateTime = post.getModifiedDateTime();
		this.postFileList = post.getPostFiles();
		this.category = post.getCategory();
	}

}
