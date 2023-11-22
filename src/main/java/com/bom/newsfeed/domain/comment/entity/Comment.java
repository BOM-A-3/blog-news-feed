
package com.bom.newsfeed.domain.comment.entity;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 1000)
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public Comment (CommentRequestDto commentRequestDto, Member member) {
		this.comment = commentRequestDto.getComment();
		this.member = member;
	}

	public void update (CommentRequestDto commentRequestDto) {
		this.comment = commentRequestDto.getComment();
	}

	public void initPost (Post post) {
		this.post = post;
	}
}