package lee.rentalbook.api.rental.management.service;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.book.service.BookService;
import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.api.rental.management.dto.RentalDto;
import lee.rentalbook.domain.rental.repository.RentalRepository;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lee.rentalbook.global.exception.ErrorCode.*;

/**
 * @file RentalManagementService
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-20
 */
@Service
@RequiredArgsConstructor
public class RentalManagementService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final BookService bookService;

    private static final int RENTAL_QUANTITY = -1;

    /**
     * 책 대여
     * 책 수량 확인 -> 빌릴 수 있으면 저장, 없으면 에러코드
     * @param userKey
     * @param rentalDto
     */
    @Transactional
    public void createRental(String userKey, RentalDto rentalDto) {
        User user = userService.findByUserKey(userKey);
        Book book = bookService.findById(rentalDto.getBookId());

        //책의 재고 확인
        if(book.getBookDetails().getQuantity() <= 0){
            throw new CustomException(BOOK_NOT_ENOUGH_QUANTITY);
        }
        //3권이상 빌렸는지 확인
        if (isUserBorrowedAtLEastThreeBooks(userKey)) {
            throw new CustomException(RENTAL_BOOK_IS_FULL);
        } else {
            book.getBookDetails().reduceQuantity(RENTAL_QUANTITY);
            Rental rental = Rental.builder()
                    .expiredDate(rentalDto.getExpiredDate())
                    .book(book)
                    .user(user)
                    .build();
            rentalRepository.save(rental);
        }
    }

    /**
     * 책 3권이상 빌렸는지 확인
     * @param userKey
     * @return
     */
    public boolean isUserBorrowedAtLEastThreeBooks(String userKey) {
        Long borrowedBooks = rentalRepository.countBooksRentalByUser(userKey);
        return borrowedBooks >= 3;
    }

    /**
     * 책 반납
     * 책 수량 증가
     * @param userKey
     * @param bookId
     */
    @Transactional
    public void returnedRentalBook(String userKey, Long bookId) {
//        findRental(userKey);
        Rental rental = rentalRepository.returnedBook(userKey, bookId)
                .orElseThrow(() -> new CustomException(RENTAL_BOOK_NOT_FOUND));

        //해당 책에 수량 증가
        Book book = bookService.findById(bookId);
        book.getBookDetails().returnedQuantity();
        //반납일 저장
        rental.markAsReturned();
    }
}
