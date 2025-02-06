package lee.rentalbook.api.account.service;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.api.account.dto.ProfileDto;
import lee.rentalbook.api.account.dto.RegisterDto;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.api.account.dto.WithdrawDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.repository.UserRepository;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static lee.rentalbook.global.exception.ErrorCode.USER_NOT_EXISTS;

/**
 * @file AccountService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new CustomException(USER_NOT_EXISTS));
//    }

    public Map<String, Boolean> isEmailExists(String email) {
        Map<String, Boolean> duplicateEmail = new HashMap<>();
        duplicateEmail.put(email, userRepository.existsByEmail(email));
        return duplicateEmail;
    }
    //사용자
    /**
     * 회원가입
     * @param registerDto
     */
    public void register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new CustomException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new CustomException(ErrorCode.CART_IS_EMPTY);
        }
        User register = registerDto.toEntity(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(register);
    }

//    /**
//     * 회원탈퇴
//     * @param withdrawDto
//     */
//    public void withdraw(WithdrawDto withdrawDto) {
//        User user = findByEmail(withdrawDto.getEmail());
//        userRepository.delete(user);//임시 -> 사용자 관련된 정보 다 삭제하고 수량도 원래대로 돌려야함
//    }

    /**
     * 사용자 상세정보 조회
     * @param userKey
     * @return
     */
    public ProfileDto getProfile(String userKey) {
        User findUser = userService.findByUserKey(userKey);
        return ProfileDto.builder()
                .name(findUser.getName())
                .address(findUser.getUserDetails().getAddress())
                .phoneNumber(findUser.getUserDetails().getPhoneNumber())
                .build();
    }

    /**
     * 사용자 상세 정보 수정
     * @param userKey
     * @param profileDto
     */
    public void modifyProfile(String userKey, ProfileDto profileDto) {
        User findUser = userService.findByUserKey(userKey);
        //사용자 검증 후
        findUser.getUserDetails().modify(profileDto);
    }

    //관리자
    /**
     * 사용자 목록 조회
     * @param userRoles
     * @return
     */
    public Page<UserDto> userListInquiry(UserRoles userRoles, int page, int size) {
        //관리자인지 확인
        userService.findAdmin(userRoles);
        //관리자가 맞으면
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRoles(UserRoles.USER, pageable);
    }
}
