package lee.rentalbook.global.auth.oauth;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.UserDetails;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.global.exception.AuthException;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

import static lee.rentalbook.global.exception.ErrorCode.ILLEGAL_REGISTRATION_ID;
import static lee.rentalbook.global.auth.utils.KeyGenerator.generateKey;

/**
 * @file OAuthInfo.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Builder
public record OAuth2UserInfo(
        String name,
        String email
){
    public static OAuth2UserInfo of(String registerId, Map<String, Object> attributes) {
        return switch (registerId) {
            case "google" -> ofGoogle(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }

    public User toEntity() {
        String temporaryPassword = UUID.randomUUID().toString();  // UUID로 임시 비밀번호 생성

        UserDetails userDetails = UserDetails.builder()
                .phoneNumber("010-0000-0000")
                .build();
        return User.builder()
                .name(name)
                .email(email)
                .roles(UserRoles.USER)
                .userKey(generateKey())
                .password(temporaryPassword)
                .userDetails(userDetails)
                .build();
    }

}
