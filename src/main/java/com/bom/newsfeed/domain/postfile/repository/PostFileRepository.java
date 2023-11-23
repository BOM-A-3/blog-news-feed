package com.bom.newsfeed.domain.postfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bom.newsfeed.domain.postfile.entity.PostFile;

public interface PostFileRepository extends JpaRepository<PostFile,Long> {


}
