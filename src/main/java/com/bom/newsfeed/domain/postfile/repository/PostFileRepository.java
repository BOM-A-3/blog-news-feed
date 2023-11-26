package com.bom.newsfeed.domain.postfile.repository;

import java.io.File;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.PostFile;


@Repository
public interface PostFileRepository extends JpaRepository<PostFile,Long> {

	// 해당하는 포스트 아이디에 있는 PostFile 가져오기
	PostFile findPostFileByIdAndPost_Id(Long fileId, Long postId);
	List<PostFile> findAllByPostId(Long postId);


}
