package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class NotFoundInfoException extends ApiException {
	public NotFoundInfoException(){super(ErrorCode.NOT_INFO_MESSAGE);
	}
}
