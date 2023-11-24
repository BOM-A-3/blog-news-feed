package com.bom.newsfeed.global.common.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class DefaultRes<T> {
    @Schema(
        description = "상태코드",
        example = "200"
    )

    public final int statusCode;

    @Schema(
        description = "응답 메시지",
        example = "응답 메시지"
    )
    public final String message;

    @Schema(description = "발생일")
    public final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "데이터", nullable = true)
    public final T data;

    protected DefaultRes(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}