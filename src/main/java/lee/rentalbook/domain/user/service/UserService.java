package lee.rentalbook.domain.user.service;

import lee.rentalbook.api.login.dto.LoginDto;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.repository.UserRepository;
import lee.rentalbook.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static lee.rentalbook.global.exception.ErrorCode.*;

/**
 * @file UserService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 사용자 찾기 -> 없으면 400에러
     * @param userKey
     * @return
     */
    public User findByUserKey(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new CustomException(USER_NOT_EXISTS));
    }

    /**
     * 사용자 찾기
     * @param userId
     * @return
     */
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_EXISTS));
    }

    public void login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(ID_NOT_MATCH));
        if (!passwordEncoder.matches(user.getPassword(), loginDto.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
    }

    //관리자 기능

    /**
     * 관리자 찾기
     * @param userRoles
     * @return
     */
    public User findAdmin(UserRoles userRoles) {
        if (userRoles != UserRoles.ADMIN) {
            throw new CustomException(USER_NOT_ADMIN);
        }
        return userRepository.findAdminByRoles(userRoles)
                .orElseThrow(() -> new CustomException(USER_NOT_ADMIN));
    }

}
