package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class MemberNotFoundException extends ApiException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
