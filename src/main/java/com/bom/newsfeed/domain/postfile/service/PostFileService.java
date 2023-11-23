package com.bom.newsfeed.domain.postfile.service;

import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.postfile.repository.PostFileRepository;

@Service
public class PostFileService {

	private final PostFileRepository postFileRepository;

	public PostFileService(PostFileRepository postFileRepository) {
		this.postFileRepository = postFileRepository;
	}


}
