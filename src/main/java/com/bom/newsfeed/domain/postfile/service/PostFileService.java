package com.bom.newsfeed.domain.postfile.service;

import static com.bom.newsfeed.global.util.MemberUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.postfile.entity.FileType;
import com.bom.newsfeed.domain.postfile.entity.PostFile;
import com.bom.newsfeed.domain.postfile.repository.PostFileRepository;
import com.bom.newsfeed.domain.postfile.util.S3Uploader;
import com.bom.newsfeed.global.exception.NotFoundFileException;

@Service
public class PostFileService {

	private final PostFileRepository postFileRepository;
	private final S3Uploader s3Uploader;
	private static final String FILE_PATH = "static/file-image" ;

	public PostFileService(PostFileRepository postFileRepository, S3Uploader s3Uploader) {
		this.postFileRepository = postFileRepository;
		this.s3Uploader = s3Uploader;
	}

	@Transactional
	public Post createFile(List<MultipartFile> files, Post post) {
		List<String> urlList = new ArrayList<>();

		if (!files.isEmpty()){
			urlList = s3Uploader.uploadFileToS3(files, FILE_PATH);
		}
		List<String> extension = inputFileExtension(files);
		List<FileType> fileTypes = inputFileType(extension);
		post.addPostFile(inputPostFile(urlList, fileTypes)); // 입력받은 PostFile을 post에 저장
		return post;
	}

	@Transactional
	public void deleteFile(Long postId, Long fileId, MemberDto memberDto) throws NotFoundFileException{
		PostFile postFiles = postFileRepository.findPostFileByIdAndPost_Id(fileId, postId);
		deletePostFileNullCheck(postFiles);

		String targetName = postFiles.getPost().getMember().getUsername();
		String userName = memberDto.getUsername();
		matchedMember(targetName,userName);

		String deleteUrl = postFiles.getUrl();
		postFileRepository.delete(postFiles);
		s3Uploader.deleteS3(deleteUrl);
	}

	@Transactional()
	public Post updateFile(Post post, List<String> postUpdateFileList){
		List<PostFile> postFileList = postFileRepository.findAllByPostId(post.getId());
		List<PostFile> deleteFiles = deleteCheckFile(postFileList,postUpdateFileList);

		post.removePostFile(deleteFiles);
		deleteListFile(deleteFiles); // s3파일 삭제
		return post;
	}

	// s3 파일 삭제
	public void deleteListFile(List<PostFile> deleteFiles)
	{
		for (PostFile deleteFile: deleteFiles) {
			s3Uploader.deleteS3(deleteFile.getUrl());
		}
	}



	// 업데이트할 파일과 기존에 있던 파일 비교후 삭제 목록 생성
	public List<PostFile> deleteCheckFile( List<PostFile> postFileList, List<String>postUpdateFileList){
		List<PostFile> deleteFileList = new ArrayList<>();
		boolean flag;
		for (PostFile postFile : postFileList) {
			flag = false;
			for (String s : postUpdateFileList) {
				if (postFile.getUrl().equals(s)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				deleteFileList.add(postFile);
			}
		}

		return deleteFileList;
	}


	// 받은 파일의 형식을 저장
	public List<String> inputFileExtension(List<MultipartFile> files) {
		List<String> extension = new ArrayList<>();
		for (MultipartFile file : files) {
			String filesNickName = file.getOriginalFilename();
			extension.add(filesNickName.substring(filesNickName.lastIndexOf(".")+1));
		}
		return extension;
	}

	// 파일 파일의 형식을 FileType을 통해 형식 분류
	public List<FileType> inputFileType(List<String> extensions){
		List<FileType> fileTypes = new ArrayList<>();
		for (String extension: extensions) {
			fileTypes.add(FileType.getFileTypeByExtension(extension));
		}
		return fileTypes;
	}

	// 받은 s3Url과 FileType을 PostFile에 입력
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

	// 파일이 있는지 체크
	public void deletePostFileNullCheck(PostFile postFile) throws NotFoundFileException{
		if(postFile == null){
			throw new NotFoundFileException();
		}
	}





}
