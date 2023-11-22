package com.bom.newsfeed.global.exception;

public class AlreadyExistNicknameException extends ApiException {
    public AlreadyExistNicknameException() {

        super(ErrorCode.ALREADY_EXIST_NICKNAME);
    }
}
