package lee.rentalbook.api.login.controller;

import jakarta.validation.Valid;
import lee.rentalbook.api.login.dto.AccessTokenDto;
import lee.rentalbook.api.login.dto.LoginDto;
import lee.rentalbook.domain.redis.service.TokenService;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class LoginController {

    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/auth/login")
    public ResponseEntity<LoginDto> login(@Valid LoginDto loginDto) {
        userService.login(loginDto);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/auth/success")
    public ResponseEntity<AccessTokenDto> loginSuccess(@Valid AccessTokenDto accessTokenDto) {
        return ResponseEntity.ok().body(accessTokenDto);
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        tokenService.deleteRefreshToken(userDetails.getUsername());
        return ResponseEntity.noContent().build();//204 상태코드
    }
}
