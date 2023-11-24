package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class InvalidMyPostLikeException extends ApiException {
	public InvalidMyPostLikeException(){
		super(ErrorCode.INVALID_MY_POST_LIKE);;
	}
}
