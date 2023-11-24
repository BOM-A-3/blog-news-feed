package com.bom.newsfeed.global.util;

import com.bom.newsfeed.global.exception.BadCredentialException;
import com.bom.newsfeed.global.exception.InvalidMyPostLikeException;

public class MemberUtil {
	private MemberUtil(){
	}

	public static void matchedMember(String userName, String targetName) throws BadCredentialException {
		if(!userName.equals(targetName))
			throw new BadCredentialException();
	}

	public static void notMatchedMember(String userName, String targetName) throws InvalidMyPostLikeException
	{
		if(userName.equals(targetName))
			throw new InvalidMyPostLikeException();

	}

}
