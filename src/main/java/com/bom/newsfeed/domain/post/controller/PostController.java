package com.bom.newsfeed.domain.post.controller;

import java.time.chrono.IsoEra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bom.newsfeed.domain.post.service.PostService;

@RequestMapping("/api")
@RestController
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}




}
