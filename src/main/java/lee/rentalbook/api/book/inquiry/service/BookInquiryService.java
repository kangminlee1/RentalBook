package lee.rentalbook.api.book.inquiry.service;

import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.book.dto.BookDto;
import lee.rentalbook.domain.book.repository.BookRepository;
import lee.rentalbook.domain.book.service.BookService;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.repository.OrderRepository;
import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.domain.rental.repository.RentalRepository;
import lee.rentalbook.domain.rental.service.RentalService;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @file BookInquiryService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class BookInquiryService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final BookService bookService;
    private final RentalRepository rentalRepository;
    private final OrderRepository orderRepository;
    private final RentalService rentalService;


    //공통

    /**
     * 검색 개행문자 제거
     * @param title
     * @return
     */
    public String removeTitleSpace(String title) {
        title = title.replaceAll("\\s", "");
        return title;
    }

    /**
     * 책 상세정보 조회
     * @param bookDetailsId
     * @return
     */
    public BookDetailsDto getBookInformation(Long bookDetailsId) {
        //책 존재하는지 확인 후 반환
        return BookDetailsDto.of(bookService.findBook(bookDetailsId));
    }

    /**
     * 사용자가 대여한 책 조회
     * @param userKey
     * @param page
     * @param size
     * @return
     */
    public Page<BookDto> getRentalBookList(String userKey, int page, int size) {
        //본인 확인
        userService.findByUserKey(userKey);
        //사용자 대여 정보 가져옴
        List<Rental> rentalList = rentalService.findRental(userKey);
        //반납 안한 것들 만 return -> rental의 returnDate가 없는 것만
        List<BookDto> rentedBooks = rentalList.stream()
                .filter(rental -> rental.getReturnDate() == null)
                .map(rental -> new BookDto(rental.getBook()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(rentedBooks, pageable, rentedBooks.size());

    }

    /**
     * 책 제목으로 검색
     * @param title
     * @param page
     * @param size
     * @return
     */
    public Page<BookDto> searchBooksByTitle(String title, int page, int size) {
        title = removeTitleSpace(title);
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByBookWithTitle(title, pageable);
    }

    /**
     * 챡 카테고리로 검색
     * @param category
     * @param page
     * @param size
     * @return
     */
    public Page<BookDto> searchBooksByCategory(String category, int page, int size) {
        category = removeTitleSpace(category);
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByBookWithCategory(category, pageable);
    }

    /**
     * 책 작가로 검색
     * @param author
     * @param page
     * @param size
     * @return
     */
    public Page<BookDto> searchBooksByAuthor(String author, int page, int size) {
        author = removeTitleSpace(author);
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByBookWithAuthor(author, pageable);
    }

    /**
     * 사용자가 반납한 책 조회
     * @param userKey
     * @param page
     * @param size
     * @return
     */
    public Page<BookDto> returnedRentalBookList(String userKey, int page, int size) {
        //본인 확인
        userService.findByUserKey(userKey);
        //본인이 반납한 책들인지 확인 -> 반닙일 존재하면 반납한 책 -> 조건은 반납 한 것들
        List<Rental> rentalList = rentalService.findRental(userKey);
        List<BookDto> returnedBooks = rentalList.stream()
                .filter(rental -> rental.getReturnDate() != null)
                .map(rental -> new BookDto(rental.getBook()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(returnedBooks, pageable, returnedBooks.size());
    }

    //관리자
    /**
     * 인기 책 조회(주문 + 대여 횟수)
     * @param userRoles
     * @return
     */
    public List<BookDto> getPopularBookList(UserRoles userRoles) {
        userService.findAdmin(userRoles);

        //사용자의 주문과 대여 내역을 모두 가져옴
        List<Rental> rentalList = rentalRepository.findAll();
        List<Orders> ordersList = orderRepository.findAllByUserRoles(UserRoles.USER);

        //bookId 기준으로 찾아서 count 후 합한 값으로 인기 순 정렬

        //bookID 기준으로 그룹화 후 대여 횟수 계산
        Map<Long, Long> rentalCount = rentalList.stream()
                .collect(Collectors.groupingBy(rental -> rental.getBook().getBookId(), Collectors.counting()));

        //bookId 기준으로 그룹화 후 주문 횟수 계산
        Map<Long, Long> ordersCount = ordersList.stream()
                .flatMap(orders -> orders.getOrderItems().stream())
                .collect(Collectors.groupingBy(orderItem -> orderItem.getBook().getBookId(), Collectors.counting()));

        Map<Long, Long> popularBooks = new HashMap<>(rentalCount);
        ordersCount.forEach((bookId, count) -> popularBooks.merge(bookId, count, Long::sum));

        return popularBooks.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())//내림차순으로 정렬
                .map(entry -> new BookDto(bookService.findById(entry.getKey())))//bookId로 해당 책 Dto 반환
                .toList();
    }

}
