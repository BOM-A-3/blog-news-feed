package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class AlreadyExistMemberException extends ApiException {
    public AlreadyExistMemberException() {

        super(ErrorCode.ALREADY_EXIST_MEMBER);
    }
}
