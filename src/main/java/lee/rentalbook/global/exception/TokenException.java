package lee.rentalbook.global.exception;

/**
 * @file TokenException.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
public class TokenException extends CustomException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
