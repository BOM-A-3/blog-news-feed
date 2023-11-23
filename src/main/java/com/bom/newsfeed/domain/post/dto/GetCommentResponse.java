package com.bom.newsfeed.domain.post.dto;

import java.time.LocalDateTime;

import com.bom.newsfeed.domain.comment.entity.Comment;

import lombok.Getter;

@Getter
public class GetCommentResponse {

	private final Long id;

	private final String content;

	private final String memberName;

	private final LocalDateTime createdDateTime;

	private final LocalDateTime modifiedDateTime;

	public GetCommentResponse(Comment comment) {
		this.id= comment.getId();
		this.content = comment.getComment();
		this.memberName = comment.getMember().getNickname();
		this.createdDateTime = comment.getCreatedDateTime();
		this.modifiedDateTime = comment.getModifiedDateTime();

	}

}
