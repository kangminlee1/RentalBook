package lee.rentalbook.api.book.management.controller;

import lee.rentalbook.api.book.management.service.BookManagementService;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class BookManagementController {

    private final BookManagementService bookManagementService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createBook(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "bookDetailsDto") BookDetailsDto bookDetailsDto
            ){

        User user = userService.findByUserKey(userDetails.getUsername());
        bookManagementService.createBook(user.getRoles(), bookDetailsDto);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/modify/bookinfo")
    public ResponseEntity<Object> modifyBookInfo(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "bookDetailsId") Long bookDetailsId
            , @RequestParam(name = "bookDetailsDto") BookDetailsDto bookDetailsDto
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        bookManagementService.modifyBook(bookDetailsId, user.getRoles(), bookDetailsDto);
        return ResponseEntity.ok(null);
    }
}
