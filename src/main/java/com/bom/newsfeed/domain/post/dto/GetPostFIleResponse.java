package com.bom.newsfeed.domain.post.dto;

import java.util.List;

import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.FileType;
import com.bom.newsfeed.domain.postfile.entity.PostFile;

import lombok.Getter;

@Getter
public class GetPostFIleResponse {

	private final String url;

	private final FileType filetype;

	public GetPostFIleResponse(PostFile postFile) {
		this.url = postFile.getUrl();
		this.filetype = postFile.getFiletype();
	}


}
