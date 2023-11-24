package com.bom.newsfeed.domain.postfile.service;

import static com.bom.newsfeed.global.exception.ErrorCode.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bom.newsfeed.domain.postfile.dto.PostFileRequestDto;
import com.bom.newsfeed.domain.postfile.entity.FileType;
import com.bom.newsfeed.domain.postfile.entity.PostFile;
import com.bom.newsfeed.domain.postfile.repository.PostFileRepository;
import com.bom.newsfeed.domain.postfile.util.S3Uploader;
import com.bom.newsfeed.global.exception.ApiException;

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
		List<String> url = new ArrayList<>();
		if (!files.isEmpty()){
			url = s3Uploader.uploadFileToS3(files, "static/file-image");
		}
		List<PostFile> postFiles;
		List<FileType> fileTypes = inputFileType(extension);

		postFiles = inputPostFile(url, fileTypes);
		postFileRepository.saveAll(postFiles);
		// S3 통과후 내 DB 저장
		// url PostFileRepo에 저장
		// 코드 작성
	}

	@Transactional
	public void deleteFile(Long postId, Long fileId) throws Exception{
		List<PostFile> postFiles = postFileRepository.findByPostId(postId);

		String deleteUrl = postFiles.get(fileId.intValue()).getUrl();
		FileType deleteFileType = postFiles.get(fileId.intValue()).getFiletype();

		PostFile PostFile = new PostFile(deleteUrl,deleteFileType);

		s3Uploader.deleteS3(deleteUrl);


	}




	public List<String> inputFileExtension(List<MultipartFile> files) {
		List<String> extension = new ArrayList<>();
		for(int i = 0; i < files.size(); i++) {
			extension.add(StringUtils.getFilenameExtension(files.get(i).getName()));
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

	public List<PostFile> inputPostFile(List<String> urls, List<FileType> fileTypes) throws ApiException {
		List<PostFile> postFiles = new ArrayList<>();
		if(urls.size() == fileTypes.size())
		{
			for (int i = 0; i < urls.size(); i++) {
				postFiles.add(new PostFile(urls.get(i),fileTypes.get(i)));
			}
			return postFiles;
		}
		else throw new ApiException(INVALID_VALUE);
	}
}
