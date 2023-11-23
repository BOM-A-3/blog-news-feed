package com.bom.newsfeed.global.exception;

public class InvalidRefreshTokenException extends ApiException{
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
