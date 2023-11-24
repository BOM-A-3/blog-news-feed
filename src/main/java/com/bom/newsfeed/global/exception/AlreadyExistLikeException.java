package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class AlreadyExistLikeException extends ApiException{
	public AlreadyExistLikeException(){super(ErrorCode.ALREADY_EXIST_LIKE);}
}
