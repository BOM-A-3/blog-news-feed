package com.bom.newsfeed.global.common.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    /*회원 응답*/
    SIGNUP_MEMBER(CREATED, "회원 가입 완료."),
    VERIFY_NICKNAME(OK, "사용가능한 닉네임입니다."),
    VERIFY_USERNAME(OK, "사용가능한 아이디입니다."),
    UPDATE_PROFILE(OK, "프로필 수정 완료."),
    /*게시물 응답*/
    /*댓글 응답*/
    CREATED_COMMENT(CREATED,"댓글 작성 완료"),
    UPDATE_COMMENT(OK,"댓글 수정 완료"),
    DELETE_COMMENT(NO_CONTENT, "댓글 삭제 완료");
    private final HttpStatus httpStatus;
    private final String detail;
}
