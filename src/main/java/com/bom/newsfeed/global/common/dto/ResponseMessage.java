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
    public static final String NOT_ACCEPT_MESSAGE ="접근 권한이 없습니다.";
    public static final String NOT_INFO_MESSAGE = "정보를 찾을 수 없습니다.";
    public static final String POST_LIST_NULL_MESSAGE ="목록이 비어 있습니다.";
    public static final String NOT_MY_POST_INPUT_LIKE = "본인 게시글에는 좋아요를 할수 없습니다.";
    public static final String LIKE_INPUT_NULL = "좋아요를 누른적이 없습니다.";
    public static final String LIKE ="좋아요";
    public static final String DELETE_LIKE= "좋아요 취소";
    public static final String ALREADY_INPUT_LIKE ="이미 좋아요를 하셨습니다.";
}
