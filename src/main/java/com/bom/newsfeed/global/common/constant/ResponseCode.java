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
    GET_PROFILE(OK,"프로필 조회 완료"),
    /*게시물 응답*/
    GET_FEED(OK,"피드 조회 완료"),
    CREATE_POST(OK, "포스트 생성 완료"),
    GET_POST(OK,"포스트 조회 성공"),
    GET_SELECT_POST(OK, "포스트 선택 조회 성공"),
    POST_UPDATE(OK, "포스트 업데이트 성공" ),
    POST_DELETE(OK,"포스트 삭제 성공"),
    /*댓글 응답*/
    CREATED_COMMENT(CREATED,"댓글 작성 완료"),
    UPDATE_COMMENT(OK,"댓글 수정 완료"),
    DELETE_COMMENT(NO_CONTENT, "댓글 삭제 완료"),
    /*파일 응답*/
    DELETE_FILE(OK,"파일 삭제 완료"),
    /*좋아요 응답*/
    ADD_LIKE(CREATED, "좋아요 등록"),
    DELETE_LIKE(NO_CONTENT, "좋아요 취소");

    private final HttpStatus httpStatus;
    private final String detail;
}
