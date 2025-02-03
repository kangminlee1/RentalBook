package lee.rentalbook.global.auth.service;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.repository.UserRepository;
import lee.rentalbook.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static lee.rentalbook.global.exception.ErrorCode.NO_ACCESS;
import static lee.rentalbook.global.exception.ErrorCode.USER_NOT_FOUND;

/**
 * @file AuthenticationService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    /**
     * 사용자 조회 -> 추후에 다른 곳이 적합한거 같으면 삭제
     * @param userKey
     * @return
     */
    public User getUserOrThrow(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));
    }

    /**
     * 접근 권한 체크
     * @param userKey
     * @param user
     */
    public void checkAccess(String userKey, User user) {
        if (!user.getUserKey().equals(userKey)) {
            throw new AuthException(NO_ACCESS);
        }
    }
}
