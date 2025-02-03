package lee.rentalbook.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

/**
 * @file ErrorCode.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-ing
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //사용자 관련
    USER_NOT_EXISTS(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "사용자가 존재하지 않습니다"),
    PASSWORD_NOT_MATCH(BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    ID_NOT_MATCH(BAD_REQUEST, "아이디가 잘못되었습니다."),
    EMAIL_ALREADY_EXISTS(BAD_REQUEST, "해당 이메일은 이미 존재합니다."),

    //책 관련
    BOOK_NOT_FOUND(NOT_FOUND, "해당 책이 존재하지 않습니다."),
    BOOK_NOT_ENOUGH_QUANTITY(BAD_REQUEST, "해당 책의 수량이 부족합니다."),

    //대여 관련
    RENTAL_NOT_FOUND(NOT_FOUND, "대여한 것이 없습니다."),
    RENTAL_BOOK_NOT_FOUND(NOT_FOUND, "해당 책을 대여하지 않았습니다."),
    RENTAL_BOOK_IS_FULL(BAD_REQUEST, "책을 3권이상 대여할 수 없습니다."),

    //장바구니 관련
    CART_IS_EMPTY(NOT_FOUND, "해당 사용자의 장바구니는 비어있습니다."),
    CART_ITEM_NOT_FOUND(NOT_FOUND, "장바구니에 해당 상품이 존재하지 않습니다."),

    //주문 관련
    ORDER_NOT_FOUND(NOT_FOUND,"해당 주문이 존재하지 않습니다."),
    QUANTITY_IS_EMPTY(BAD_REQUEST, "수량이 부족합니다."),

    //배송 관련
    DELIVERY_NOT_FOUND(NOT_FOUND, "해당 주문은 배송중이지 않습니다."),
    DELIVERY_IS_NOT_READY(BAD_REQUEST, "해당 상품은 준비중이 아니라 수정할 수 없습니다."),

    //권한 관련
    NO_ACCESS(UNAUTHORIZED, "접근 권한이 없습니다."),
    USER_NOT_ADMIN(UNAUTHORIZED, "관리자가 아닙니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_JWT_SIGNATURE(UNAUTHORIZED, "잘못된 JWT Signature 입니다."),
    INVALID_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다."),
    ILLEGAL_REGISTRATION_ID(UNAUTHORIZED, "잘못된 세션 로그인 아이디입니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
