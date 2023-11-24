package com.bom.newsfeed.domain.postfile.service;

import static com.bom.newsfeed.global.util.MemberUtil.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.FileType;
import com.bom.newsfeed.domain.postfile.entity.PostFile;
import com.bom.newsfeed.domain.postfile.repository.PostFileRepository;
import com.bom.newsfeed.domain.postfile.util.S3Uploader;

@Service
public class PostFileService {

	private final PostFileRepository postFileRepository;
	private final S3Uploader s3Uploader;

	public PostFileService(PostFileRepository postFileRepository, S3Uploader s3Uploader) {
		this.postFileRepository = postFileRepository;
		this.s3Uploader = s3Uploader;
	}

	@Transactional
	public void createFile(List<MultipartFile> files ) {

		List<String> extension = inputFileExtension(files);
		List<String> urlList = new ArrayList<>();
		if (!files.isEmpty()){
			urlList = s3Uploader.uploadFileToS3(files, "static/file-image");
		}

		List<FileType> fileTypes = inputFileType(extension);
		List<PostFile> postFiles;

		postFiles = inputPostFile(urlList, fileTypes);
		postFileRepository.saveAll(postFiles);
		// S3 통과후 내 DB 저장
		// url PostFileRepo에 저장
		// 코드 작성
	}

	@Transactional
	public void deleteFile(Long postId, Long fileId, MemberDto memberDto) throws Exception{
		List<PostFile> postFiles = postFileRepository.findByPostId(postId);

		String targetName = postFiles.get(0).getPost().getMember().getUsername();
		String userName = memberDto.getUsername();
		matchedMember(targetName,userName);

		String deleteUrl = postFiles.get(fileId.intValue()).getUrl();
		FileType deleteFileType = postFiles.get(fileId.intValue()).getFiletype();

		PostFile postFile = new PostFile(deleteUrl,deleteFileType);
		postFileRepository.delete(postFile);
		s3Uploader.deleteS3(deleteUrl);
	}

	@Transactional
	public void updateFile(Long postId, List<String> postUpdateFileList, List<MultipartFile> updateFile) throws Exception{
		List<PostFile> postFileList = postFileRepository.findByPostId(postId);

		List<PostFile> deleteFiles;
		deleteFiles = deleteCheckFile(postFileList,postUpdateFileList);
		createFile(updateFile);
		deleteListFile(deleteFiles);
		postFileRepository.deleteAll(deleteFiles);



	}

	public void deleteListFile(List<PostFile> deleteFiles) throws Exception
	{
		for (PostFile deleteFile: deleteFiles) {
			s3Uploader.deleteS3(deleteFile.getUrl());
		}
	}


	public List<PostFile> deleteCheckFile( List<PostFile> postFileList, List<String>postUpdateFileList){
		List<PostFile> deleteFileList = new ArrayList<>();
		for (int i = 0; i < postFileList.size(); i++) {
			// 목록에 업는 파일리스트 체크(삭제할 파일 리스트)
			if(!postUpdateFileList.contains(postFileList.get(i).getUrl())) {
				deleteFileList.add(postFileList.get(i));
			}
		}
		return deleteFileList;
	}



	public List<String> inputFileExtension(List<MultipartFile> files) {
		List<String> extension = new ArrayList<>();
		for (MultipartFile file : files) {
			extension.add(StringUtils.getFilenameExtension(file.getName()));
		}
		return extension;
	}

	public List<FileType> inputFileType(List<String> extensions){
		List<FileType> fileTypes = new ArrayList<>();
		for (String extension: extensions) {
			fileTypes.add(FileType.getFileTypeByExtension(extension));
		}
		return fileTypes;
	}

	public List<PostFile> inputPostFile(List<String> urls, List<FileType> fileTypes){
		List<PostFile> postFiles = new ArrayList<>();
		if(urls.size() == fileTypes.size())
		{
			for (int i = 0; i < urls.size(); i++) {
				postFiles.add(new PostFile(urls.get(i),fileTypes.get(i)));
			}
			return postFiles;
		}
		else return null;
	}
}
