package com.bom.newsfeed.global.exception;

public class AccessDeniedException extends ApiException{
    public AccessDeniedException(String message) {
        super(ErrorCode.ACCESS_DENIED, message);
    }
}
