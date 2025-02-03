package lee.rentalbook.global.auth.utils;

import java.util.UUID;

/**
 * @file KeyGenerator.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
public final class KeyGenerator {
    //userKey 생성기
    public static String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
