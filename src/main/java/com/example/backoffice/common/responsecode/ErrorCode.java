package com.example.backoffice.common.responsecode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ROLE("NO_SUCH_ROLE", "존재하지 않는 관리자 역할입니다."),
    NO_SUCH_STATUS("NO_SUCH_STATUS", "존재하지 않는 관리자 상태입니다."),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    NO_SUCH_USER("NO_SUCH_USER", "존재하지 않는 유저입니다."),
    ADMIN_NOT_FOUND("ADMIN_NOT_FOUND", "관리자가 존재하지 않습니다."),
    INSUFFICIENT_ADMIN_ROLE ("INSUFFICIENT_ADMIN_ROLE", "필요한 관리자 역할이 없습니다"),
    PASSWORD_CONFIRM_MISMATCH_ERROR("PASSWORD_CONFIRM_MISMATCH_ERROR", "새 비밀번호가 일치하지 않습니다."),
    NO_SUCH_PRODUCT("NO_SUCH_PRODUCT", "존재하지 않는 상품입니다."),
    NOT_AVAILABLE("NOT_AVAILABLE", "단종된 상품은 주문할 수 없습니다."),
    OUT_OF_STOCK("OUT_OF_STOCK", "품절된 상품은 주문할 수 없습니다."),
    INSUFFICIENT_STOCK("INSUFFICIENT_STOCK", "재고가 주문 수량보다 부족하여 주문할 수 없습니다."),
    NO_SUCH_ORDER("NO_SUCH_ORDER", "존재하지 않는 주문입니다."),
    UNABLE_CANCEL("UNABLE_CANCEL", "배송중 또는 배송완료 주문은 취소할 수 없습니다."),
    INVALID_RATING("INVALID_RATING", "평점은 1~5점입니다."),
    REVIEW_NOT_FOUND("REVIEW_NOT_FOUND", "리뷰가 존재하지 않습니다"),
    ALREADY_COMPLETED("ALREADY_COMPLETED", "이미 완료된 주문입니다."),
    ALREADY_CANCELLED("ALREADY_CANCELLED","취소된 주문은 상태를 변경할 수 없습니다.");
    private final String code;
    private final String message;
}
