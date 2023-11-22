package com.bom.newsfeed.domain.comment.dto;

import java.time.LocalDateTime;

import com.bom.newsfeed.domain.comment.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {

	private final Long id;
	private final String username;
	private final String comment;
	private final LocalDateTime createdDateTime;
	private final LocalDateTime modifiedDateTime;

	public CommentResponseDto (Comment comment) {
		this.id = comment.getId();
		this.username = comment.getMember().getUsername();
		this.comment = comment.getComment();
		this.createdDateTime = comment.getCreatedDateTime();
		this.modifiedDateTime = comment.getModifiedDateTime();
	}
}
