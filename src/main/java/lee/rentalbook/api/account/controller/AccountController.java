package lee.rentalbook.api.account.controller;

import lee.rentalbook.api.account.dto.RegisterDto;
import lee.rentalbook.api.account.service.AccountService;
import lee.rentalbook.domain.user.repository.UserRepository;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static lee.rentalbook.global.exception.ErrorCode.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<RegisterDto> register(RegisterDto registerDto) {
        if (!userRepository.existsByEmail(registerDto.getEmail())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        } else {
            accountService.register(registerDto);
        }
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/join/check")
    public ResponseEntity<Map<String, Boolean>> checkEmail(String email) {
        Map<String, Boolean> duplicatedEmail = accountService.isEmailExists(email);
        return ResponseEntity.ok().body(duplicatedEmail);
    }
    
}
