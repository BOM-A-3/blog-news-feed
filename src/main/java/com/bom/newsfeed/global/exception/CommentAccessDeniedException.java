package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class CommentAccessDeniedException extends ApiException {
	public CommentAccessDeniedException() {
		super(ErrorCode.COMMENT_ACCESS_DENIED);
	}
}