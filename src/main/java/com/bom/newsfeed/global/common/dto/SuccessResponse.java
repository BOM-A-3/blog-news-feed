package com.bom.newsfeed.global.common.dto;

import com.bom.newsfeed.global.common.constant.ResponseCode;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponse<T> extends DefaultRes<T> {


	@Builder
	public SuccessResponse(ResponseCode responseCode, T data) {
		super(
			responseCode.getHttpStatus().value(),
			responseCode.getDetail(),
			data
		);
	}
}
