package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class FollowingNotFoundException extends ApiException{
	public FollowingNotFoundException() {
		super(ErrorCode.FOLLOWING_NOT_FOUND);
	}
}
