package com.bom.newsfeed.global.util;

public class MemberUtil {
	private MemberUtil(){
	}

	public static void matchedMember(String userName, String targetName, String errorMessage) throws IllegalAccessException {
		if(!userName.equals(targetName))
			throw new IllegalAccessException(errorMessage);
	}

	public static void notMatchedMember(String userName, String targetName, String errorMessage) throws IllegalAccessException
	{
		if(userName.equals(targetName))
			throw new IllegalAccessException(errorMessage);

	}

}
