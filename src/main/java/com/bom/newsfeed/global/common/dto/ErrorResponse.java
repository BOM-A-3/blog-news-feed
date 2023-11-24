package com.bom.newsfeed.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse<T> extends DefaultRes<T>{


    @Builder
    public ErrorResponse(int statusCode, String message, T data) {
        super(
            statusCode,
            message,
            data
        );
    }
}
