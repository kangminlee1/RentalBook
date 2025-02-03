package lee.rentalbook.global.exception;

/**
 * @file ErrorResponse.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
public record ErrorResponse(
        ErrorCode errorCode,
        String message
) {
}
