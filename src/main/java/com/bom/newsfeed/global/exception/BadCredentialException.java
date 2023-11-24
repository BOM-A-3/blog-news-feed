package com.bom.newsfeed.global.exception;

import com.bom.newsfeed.global.common.constant.ErrorCode;

public class BadCredentialException extends ApiException{
	public BadCredentialException(){super(ErrorCode.BAD_CREDENTIAL);}
}
