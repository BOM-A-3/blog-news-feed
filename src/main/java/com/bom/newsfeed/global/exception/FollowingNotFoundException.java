package com.bom.newsfeed.global.exception;

public class FollowingNotFoundException extends ApiException{
	public FollowingNotFoundException() {
		super(ErrorCode.FOLLOWING_NOT_FOUND);
	}
}
