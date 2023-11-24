package com.bom.newsfeed.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_VALUE(BAD_REQUEST, "값이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_MY_POST_LIKE(BAD_REQUEST,"본인 게시글에는 좋아요를 할수 없습니다."),



    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "인증 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "회원 인증 정보가 존재하지 않습니다"),
    BAD_CREDENTIAL(UNAUTHORIZED, "인증정보가 일치하지 않습니다."),

    /*403 FORBIDDEN  권한, 자원이 없음 */
    ACCESS_DENIED(FORBIDDEN, "권한이 없습니다."),
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    NOT_INFO_MESSAGE (NOT_FOUND,"정보를 찾을수 없습니다."),
    LIKE_NOT_FOUND(NOT_FOUND,"좋아요를 누른적이 없습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 회원 정보를 찾을 수 없습니다"),
    FOLLOWING_NOT_FOUND(NOT_FOUND, "팔로잉한 회원 정보를 찾을 수 없습니다"),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    ALREADY_EXIST_MEMBER(CONFLICT, "이미 존재하는 회원입니다."),
    ALREADY_EXIST_NICKNAME(CONFLICT, "이미 존재하는 닉네임입니다."),
    ALREADY_EXIST_FOLLOWING(CONFLICT, "이미 팔로잉한 회원입니다."),
    ALREADY_EXIST_LIKE(CONFLICT,"이미 좋아요를 누르셨습니다."),


    /* 500 INTERNAL_SERVER_ERROR : 서버 에러 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러입니다.");
    private final HttpStatus httpStatus;
    private final String detail;
}
