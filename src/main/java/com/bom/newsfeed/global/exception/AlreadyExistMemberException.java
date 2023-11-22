package com.bom.newsfeed.global.exception;

public class AlreadyExistMemberException extends ApiException {
    public AlreadyExistMemberException() {

        super(ErrorCode.ALREADY_EXIST_MEMBER);
    }
}
