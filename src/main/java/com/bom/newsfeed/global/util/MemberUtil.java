package com.bom.newsfeed.global.util;

import static com.bom.newsfeed.global.exception.ErrorCode.*;

import com.bom.newsfeed.global.exception.ApiException;

public class MemberUtil {
	private MemberUtil(){
	}

	public static void matchedMember(String userName, String targetName) throws ApiException {
		if(!userName.equals(targetName))
			throw new ApiException(BAD_CREDENTIAL);
	}

	public static void notMatchedMember(String userName, String targetName) throws ApiException
	{
		if(userName.equals(targetName))
			throw new ApiException(INVALID_MY_POST_LIKE);

	}

}
