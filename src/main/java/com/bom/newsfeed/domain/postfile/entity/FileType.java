package com.bom.newsfeed.domain.postfile.entity;

import java.util.ArrayList;
import java.util.List;

public enum FileType {
	IMG(List.of("jpa","jpeg","png","gif","bmp","tiff")),
	FILE(List.of());



	private final List<String> extensions;

	FileType(List<String> extensions) {
		this.extensions = extensions;
	}
	public static FileType getFileTypeByExtension(String extension) {
		extension = extension.toLowerCase(); // 입력된 확장자를 소문자로 변경

		// 각 FileType의 extensions 리스트를 확인하여 해당 확장자가 있는지 검사
		for (FileType fileType : FileType.values()) {
			if (fileType.extensions.contains(extension)) {
				return fileType;
			}
		}
		// 해당 확장자를 지원하는 FileType이 없는 경우 FILE을 반환하거나 예외 처리 가능
		return FILE; // 혹은 예외 처리 등으로 대체 가능
	}
}
