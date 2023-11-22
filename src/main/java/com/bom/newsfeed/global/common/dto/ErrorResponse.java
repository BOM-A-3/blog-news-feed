package com.bom.newsfeed.global.common.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    @Schema(description = "발생일")
    private final LocalDateTime timestamp = LocalDateTime.now();
    @Schema(
            description = "상태코드",
            example = "500"
    )
    private final int status;
    @Schema(
            description = "에러명",
            example = "INTERNAL_SERVER_ERROR"
    )
    private final String name;
    @Schema(
            description = "에러 메시지",
            example = "내부 서버 에러입니다."
    )
    private final String message;
    @Schema(description = "데이터", nullable = true)
    private final Object data;

    @Builder
    protected ErrorResponse(int status, String name, String message, Object data) {
        this.status = status;
        this.name = name;
        this.message = message;
        this.data = data;
    }
}
