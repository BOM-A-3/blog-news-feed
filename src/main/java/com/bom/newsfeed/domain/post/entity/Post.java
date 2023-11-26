package com.bom.newsfeed.domain.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.bom.newsfeed.domain.category.entity.Category;
import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.like.entity.Likes;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.dto.PostRequestDto;
import com.bom.newsfeed.domain.postfile.entity.PostFile;
import com.bom.newsfeed.global.common.entity.BaseEntity;

import jakarta.persistence.CascadeType;
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

	// 유저 정보
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	// 댓글 정보
	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	// 파일 정보
	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostFile> postFiles = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Likes> likes = new ArrayList<>();

	// 카테고리 정보
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public Post(PostRequestDto requestDto, Member member, Category category) {
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		this.member = member;
		this.category = category;
	}

	public void update(PostRequestDto requestDto, Category category)
	{
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		this.category = category;

	}
	public void addPostFile(List<PostFile> postFiles) {
		// 포스트 설정
		for (PostFile postfile: postFiles) {
			postfile.setPost(this);
		}
		this.postFiles.addAll(postFiles);

	}

	public void removePostFile(List<PostFile> removePostFiles){
		// for (PostFile postFile: removePostFiles) {
		// 	this.postFiles.remove(postFile);
		// }
		this.postFiles.removeAll(removePostFiles);
	}


	// public void addComment(Comment comment) {
	// 	comment.initPost(this);
	// 	this.comments.add(comment);
	// }



}
