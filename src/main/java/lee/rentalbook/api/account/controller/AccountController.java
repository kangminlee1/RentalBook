package lee.rentalbook.api.account.controller;

import lee.rentalbook.api.account.dto.ProfileDto;
import lee.rentalbook.api.account.dto.RegisterDto;
import lee.rentalbook.api.account.service.AccountService;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.repository.UserRepository;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static lee.rentalbook.global.exception.ErrorCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<RegisterDto> register(RegisterDto registerDto) {
        if (!userRepository.existsByEmail(registerDto.getEmail())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        } else {
            accountService.register(registerDto);
        }
        return ResponseEntity.ok().body(null);
    }

    //이메일 중복 체크
    @PostMapping("/join/check")
    public ResponseEntity<Map<String, Boolean>> checkEmail(String email) {
        Map<String, Boolean> duplicatedEmail = accountService.isEmailExists(email);
        return ResponseEntity.ok().body(duplicatedEmail);
    }

    //프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        ProfileDto profileDto = accountService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok().body(profileDto);
    }

    //프로필 수정
    @PostMapping("/modify/profile")
    public ResponseEntity<ProfileDto> modifyProfile(@AuthenticationPrincipal UserDetails userDetails
            , ProfileDto profileDto) {
        accountService.modifyProfile(userDetails.getUsername(), profileDto);
        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/admin/userList")
    public ResponseEntity<Page<UserDto>> getUserList(@AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "10") int size){
        User user = userService.findByUserKey(userDetails.getUsername());
        Page<UserDto> userDtoPage = accountService.userListInquiry(user.getRoles(), page, size);
        return ResponseEntity.ok().body(userDtoPage);
    }
    
}
