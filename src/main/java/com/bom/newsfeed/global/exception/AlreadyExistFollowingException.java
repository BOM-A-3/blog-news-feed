package com.bom.newsfeed.global.exception;

public class AlreadyExistFollowingException extends ApiException{
	public AlreadyExistFollowingException() {
		super(ErrorCode.ALREADY_EXIST_FOLLOWING);
	}
}
