package com.bom.newsfeed.domain.postfile.entity;

import com.bom.newsfeed.domain.post.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "postfile")
public class PostFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String url;

	@Enumerated(EnumType.STRING)
	private FileType filetype;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public PostFile(String url, FileType fileType) {
		this.url = url;
		this.filetype = fileType;
	}


}
