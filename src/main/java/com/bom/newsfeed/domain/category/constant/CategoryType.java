package com.bom.newsfeed.domain.category.constant;

import java.util.Arrays;

public enum CategoryType {
	LIFE("일상"),
	IT("IT"),
	TRAVEL("여행"),
	COOK("요리"),
	ETC("기타");

	private final String ko;

	CategoryType(String ko) {
		this.ko = ko;
	}




	public String getKo() {
		return ko;
	}

	public static CategoryType getType(String categoryType ){
		return Arrays.stream(CategoryType.values())
			.filter(type ->
				type.ko.equals(categoryType)).findFirst()
			.orElse(CategoryType.ETC);
	}


	public static CategoryType findByName(String categoryType) {
		return Arrays.stream(CategoryType.values())
			.filter(type ->
				type.name().equals(categoryType.toUpperCase()))
			.findFirst()
			.orElse(CategoryType.ETC);
	}
}
