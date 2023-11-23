package com.bom.newsfeed.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class SelectPostResponseDto {

	private final Long id;

	private final String title;

	private final String content;

	private final String memberName;

	private final List<GetPostFIleResponse> postFileList;

	private final LocalDateTime creatDateTime;

	private final LocalDateTime modifyDateTime;

	private final String category;

	private final List<GetCommentResponse> commentList;

	public SelectPostResponseDto(Post post){
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.memberName = post.getMember().getNickname();
		this.creatDateTime = post.getCreatedDateTime();
		this.modifyDateTime = post.getModifiedDateTime();
		this.postFileList = post.getPostFiles().stream().map(GetPostFIleResponse::new).toList();
		this.category = post.getCategory().getCategory().getKo();
		this.commentList = post.getComments().stream().map(GetCommentResponse::new).toList();
	}

}