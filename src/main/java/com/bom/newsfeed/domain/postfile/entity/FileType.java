package com.bom.newsfeed.domain.postfile.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FileType {
	IMG(Arrays.asList("jpg","jpa", "jpeg", "png", "gif", "bmp", "tiff")),
	FILE(Arrays.asList());

	private final List<String> extensions;

	FileType(List<String> extensions) {
		this.extensions = extensions;
	}

	public static FileType getFileTypeByExtension(String extension) {
		String lowerExtension = extension.toLowerCase();
		return Arrays.stream(values())
			.filter(fileType -> fileType.extensions.contains(lowerExtension))
			.findFirst()
			.orElse(FILE); // 혹은 필요에 따라 예외를 던지는 것도 가능합니다
	}
}
