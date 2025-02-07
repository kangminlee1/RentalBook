package lee.rentalbook.api.email.controller;

import lee.rentalbook.api.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    
    //Redis 추가 공부후 진행
    //현재 프로젝트가 생겨 추후 다시 개발

}
