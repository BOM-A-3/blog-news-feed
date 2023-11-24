package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class PostNotFoundException extends ApiException{
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
