package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class NotFoundFileException extends ApiException {
	public NotFoundFileException(){
		super(ErrorCode.NOT_INFO_MESSAGE);
	}
}
