package lee.rentalbook.global.exception;

/**
 * @file AuthException.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
