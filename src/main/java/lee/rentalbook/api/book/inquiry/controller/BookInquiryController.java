package lee.rentalbook.api.book.inquiry.controller;

import lee.rentalbook.api.book.inquiry.service.BookInquiryService;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.book.dto.BookDto;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookInquiry")
public class BookInquiryController {

    private final BookInquiryService bookInquiryService;
    private final UserService userService;


    @GetMapping("/bookDetails/{bookIDetailsId}")
    public ResponseEntity<BookDetailsDto> getBookDetails(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable("bookIDetailsId") Long bookIDetailsId) {
        BookDetailsDto bookDetailsDto = bookInquiryService.getBookInformation(bookIDetailsId);
        return ResponseEntity.ok(bookDetailsDto);
    }

    @GetMapping("/rentalList")
    public ResponseEntity<Page<BookDto>> getRentalBookList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<BookDto> bookDtoPage = bookInquiryService.getRentalBookList(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(bookDtoPage);
    }

    @GetMapping("/search/title")
    public ResponseEntity<Page<BookDto>> getSearchBookListByTitle(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "title") String title
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<BookDto> bookDtoPage = bookInquiryService.searchBooksByTitle(title, page, size);
        return ResponseEntity.ok(bookDtoPage);
    }

    @GetMapping("/search/category")
    public ResponseEntity<Page<BookDto>> getSearchBookListByCategory(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "category") String category
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<BookDto> bookDtoPage = bookInquiryService.searchBooksByCategory(category, page, size);
        return ResponseEntity.ok(bookDtoPage);
    }

    @GetMapping("/search/author")
    public ResponseEntity<Page<BookDto>> getSearchBookListByAuthor(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "author") String author
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<BookDto> bookDtoPage = bookInquiryService.searchBooksByAuthor(author, page, size);
        return ResponseEntity.ok(bookDtoPage);
    }

    @GetMapping("/returned/")
    public ResponseEntity<Page<BookDto>> getReturnedBookList(
            @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Page<BookDto> returnedBookPage = bookInquiryService.returnedRentalBookList(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(returnedBookPage);
    }

    //관리자
    @GetMapping("/admin/popular")
    public ResponseEntity<List<BookDto>> getPopularBookList(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        List<BookDto> bookDtoList = bookInquiryService.getPopularBookList(user.getRoles());
        return ResponseEntity.ok(bookDtoList);
    }

}
