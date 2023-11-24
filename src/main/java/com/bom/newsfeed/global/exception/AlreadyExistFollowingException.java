package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class AlreadyExistFollowingException extends ApiException{
	public AlreadyExistFollowingException() {
		super(ErrorCode.ALREADY_EXIST_FOLLOWING);
	}
}
