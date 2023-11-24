package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class AlreadyExistNicknameException extends ApiException {
    public AlreadyExistNicknameException() {

        super(ErrorCode.ALREADY_EXIST_NICKNAME);
    }
}
