package com.bom.newsfeed.domain.post.entity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.comment.repository.CommentRepository;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 제목
	@Column
	private String title;

	// 내용
	@Column(length = 2000)
	private String content;

	// @ManyToOne
	// @JoinColumn(name = "member_id")
	// private Member members;

	// @oneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<Comment> comments;

	// @oneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<PostFile> postFiles;

	public Post(PostRequestDto requestDto, Member member) {
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		// this.member = member;
	}

	public void update(PostRequestDto requestDto)
	{
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
	}
}
