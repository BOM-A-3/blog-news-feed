package com.bom.newsfeed.global.exception;

public class ChangePasswordMissMatchException extends ApiException{
    public ChangePasswordMissMatchException() {
        super(ErrorCode.INVALID_VALUE, "비밀번호가 일치하지 않습니다.");
    }
}
