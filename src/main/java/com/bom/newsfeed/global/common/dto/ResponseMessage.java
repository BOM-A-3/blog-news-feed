package com.bom.newsfeed.global.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage {
    public static final String CREATED_COMMENT = "댓글 작성 완료";
    public static final String UPDATE_COMMENT  = "댓글 수정 완료";
    public static final String DELETE_COMMENT  = "댓글 삭제 완료";
}