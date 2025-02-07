package lee.rentalbook.api.rental.inquiry.controller;

import lee.rentalbook.api.rental.inquiry.dto.OverDueDetailsDto;
import lee.rentalbook.api.rental.inquiry.service.RentalInquiryService;
import lee.rentalbook.domain.book.dto.BookDto;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental/inquiry")
public class RentalInquiryController {

    private final RentalInquiryService rentalInquiryService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Page<OverDueDetailsDto>> getRentalList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
            ){
        Page<OverDueDetailsDto> overDueDetailsDtoPage = rentalInquiryService.getRentalBooksOverDueDate(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(overDueDetailsDtoPage);
    }
    //관리자
    @GetMapping("/admin/list")
    public ResponseEntity<Page<OverDueDetailsDto>> getRentalBooksOverDueDate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        Page<OverDueDetailsDto> overDueDetailsDtoPage = rentalInquiryService.getRentalBooksOverDueDate(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(overDueDetailsDtoPage);
    }

    @GetMapping("/admin/user")
    public ResponseEntity<Page<OverDueDetailsDto>> getRentalListAndOverDueDate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        Page<OverDueDetailsDto> overDueDetailsDtoPage = rentalInquiryService.getUserRentalListAndOverDueInfo(user.getRoles(), userId, page, size);
        return ResponseEntity.ok(overDueDetailsDtoPage);
    }

    @GetMapping("/admin/book")
    public ResponseEntity<Page<UserDto>> getRentalBooks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        Page<UserDto> userDtoPage = rentalInquiryService.getRentalBooks(user.getRoles(), page, size);
        return ResponseEntity.ok(userDtoPage);
    }

    @GetMapping("/admin/overdue")
    public ResponseEntity<List<BookDto>> getOverDueDateCount(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        List<BookDto> bookDtoList = rentalInquiryService.getOverDueDateCount(user.getRoles());
        return ResponseEntity.ok(bookDtoList);
    }

    @GetMapping("/admin/current/rentaled")
    public ResponseEntity<List<BookDto>> getCurrentRentalBookCount(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        List<BookDto> bookDtoList = rentalInquiryService.getCurrentRentalBookCount(user.getRoles());
        return ResponseEntity.ok(bookDtoList);
    }

}
