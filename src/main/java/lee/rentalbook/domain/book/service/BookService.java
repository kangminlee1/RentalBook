package lee.rentalbook.domain.book.service;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.book.repository.BookDetailsRepository;
import lee.rentalbook.domain.book.repository.BookRepository;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lee.rentalbook.global.exception.ErrorCode.BOOK_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookDetailsRepository bookDetailsRepository;
//    private final UserService userService;
//    private final CartService cartService;
//    private final RentalService rentalService;
//    private final RentalRepository rentalRepository;
//    private final OrderRepository orderRepository;



    //사용자
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(BOOK_NOT_FOUND));
    }

    public BookDetails findBook(Long bookDetailsId) {
        //책이 존재하는지 확인
        BookDetails findBookDetails = bookDetailsRepository.findById(bookDetailsId)
                .orElseThrow(() -> new CustomException(BOOK_NOT_FOUND));
        bookRepository.findById(findBookDetails.getBook().getBookId())
                .orElseThrow(() -> new CustomException(BOOK_NOT_FOUND));

        return findBookDetails;
    }




}
