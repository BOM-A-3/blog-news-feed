package com.bom.newsfeed.global.util;

import com.bom.newsfeed.domain.like.entity.Like;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.entity.Post;

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
