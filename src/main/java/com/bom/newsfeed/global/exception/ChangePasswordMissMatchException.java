package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class ChangePasswordMissMatchException extends ApiException{
    public ChangePasswordMissMatchException() {
        super(ErrorCode.INVALID_VALUE, "비밀번호가 일치하지 않습니다.");
    }
}
