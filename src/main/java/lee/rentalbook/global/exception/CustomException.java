package lee.rentalbook.global.exception;

import lombok.Getter;

/**
 * @file CustomException.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}
