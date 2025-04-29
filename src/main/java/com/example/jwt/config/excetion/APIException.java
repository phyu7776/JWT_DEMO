package com.example.jwt.config.excetion;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

    private final ErrorCode errorCode;

    public APIException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Getter
    public enum ErrorCode {
        USER_INFO_INVALID(400, "사용자 정보가 올바르지 않습니다."),
        ENTITY_INVALID(400, "조회 정보가 존재하지 않습니다."),
        INVALID_PASSWORD(401, "비밀번호가 일치하지 않습니다"),
        INVALID_TOKEN(401,"Token 정보가 올바르지 않습니다."),
        USER_NOT_APPROVED(423, "관리자 승인이 필요합니다."),
        BOARD_NOT_CREATOR(423, "생성한 사용자만 삭제 가능합니다."),
        DUPLICATE_MENU(409, "이미 존재하는 메뉴입니다"),
        DUPLICATE_USER(409, "이미 존재하는 사용자입니다"),
        ;

        private final int status;
        private final String message;

        ErrorCode(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
