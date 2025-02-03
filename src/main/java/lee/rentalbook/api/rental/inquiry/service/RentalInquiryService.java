package lee.rentalbook.api.rental.inquiry.service;

import lee.rentalbook.api.rental.inquiry.dto.OverDueDetailsDto;
import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.dto.BookDto;
import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.domain.rental.repository.RentalRepository;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @file RentalInquiryService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class RentalInquiryService {

    private final UserService userService;
    private final RentalRepository rentalRepository;

    //공통

    /**
     * 연체 - 벌금 계산
     * @param returnDate
     * @return
     */
    public long calculateOverDueDate(LocalDateTime returnDate) {
        if (returnDate == null) {
            return 0;
        } else {
            long days = ChronoUnit.DAYS.between(returnDate, LocalDateTime.now());
            return days > 0 ? days : 0L;
        }
    }
    public long calculateFine(long overDueDate){
        return overDueDate * 2000L;
    }

    //사용자

    /**
     * 나의 모든 대여 목록
     * 연체일, 벌금 확인
     * @param userKey
     * @param page
     * @param size
     * @return
     */
    public Page<OverDueDetailsDto> getRentalBooksOverDueDate(String userKey, int page, int size) {
        //사용자가 맞는지 + 책을 대여한 게 있는지 확인
//        rentalService.findRental(userKey);
        Pageable pageable = PageRequest.of(page, size);
        Page<Rental> rentals = rentalRepository.findOverDueBookListByUserKey(userKey, pageable);

        List<OverDueDetailsDto> overDueDetails = rentals.getContent().stream()
                .map(rental -> {
                    long overDueDate = calculateOverDueDate(rental.getReturnDate());
                    long overDueCost = calculateFine(overDueDate);
                    Book book = rental.getBook();
                    // OverDueDetails 생성
                    return new OverDueDetailsDto(book, overDueCost, overDueDate);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(overDueDetails, pageable, rentals.getTotalElements());
    }

    //관리자
    /**
     * 모든 대여된 책 목록 및 연체 정보
     * @param userRoles
     * @param page
     * @param size
     * @return
     */
    public Page<OverDueDetailsDto> getRentalBookListAndOverDueInfo (UserRoles userRoles, int page, int size) {
        //관리인지 확인
        userService.findAdmin(userRoles);
        //모든 rental 정보
        Pageable pageable = PageRequest.of(page, size);
        Page<Rental> rentals = rentalRepository.findAll(pageable);
        //책 목록에 연체 정보 담아서 return

        List<OverDueDetailsDto> overDueDetails = rentals.getContent().stream()
                .map(rental -> {
                    long overDueDate = calculateOverDueDate(rental.getReturnDate());
                    long overDueCost = calculateFine(overDueDate);
                    Book book = rental.getBook();
                    // OverDueDetails 생성
                    return new OverDueDetailsDto(book, overDueCost, overDueDate);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(overDueDetails, pageable, rentals.getTotalElements());
    }

    /**
     * 특정 사용자 대역 내역 및 연체 정보 확인
     * @param userRoles
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public Page<OverDueDetailsDto> getUserRentalListAndOverDueInfo (UserRoles userRoles, Long userId, int page, int size) {
        //관리자인지 체크
        userService.findAdmin(userRoles);
        //특정 사용자를 찾음
        User user = userService.findByUserId(userId);
        Pageable pageable = PageRequest.of(page, size);

        Page<Rental> rentals = rentalRepository.findOverDueBookListByUserKey(user.getUserKey(), pageable);

        List<OverDueDetailsDto> overDueDetails = rentals.getContent().stream()
                .map(rental -> {
                    long overDueDate = calculateOverDueDate(rental.getReturnDate());
                    long overDueCost = calculateFine(overDueDate);
                    Book book = rental.getBook();
                    // OverDueDetails 생성
                    return new OverDueDetailsDto(book, overDueCost, overDueDate);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(overDueDetails, pageable, rentals.getTotalElements());
    }

    /**
     * 특정 책을 대여한 사용자 조회
     * @param userRoles
     * @param page
     * @param size
     * @return
     */
    public Page<UserDto> getRentalBooks(UserRoles userRoles, int page, int size) {
        //ADMIN인지 확인
        userService.findAdmin(userRoles);
        //유저 목록 조회
        List<Rental> rentalList = rentalRepository.findAll();
        List<UserDto> userDtoList = rentalList.stream()
                .map(UserDto::new)
                .toList();
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(userDtoList, pageable, rentalList.size());
    }

    //통계
    /**
     * 연체된 책 수
     * @param userRoles
     * @return
     */
    public List<BookDto> getOverDueDateCount(UserRoles userRoles) {
        userService.findAdmin(userRoles);
        //반납되지 않은 책들
        List<Rental> rentalList = rentalRepository.findByReturnDateIsNull();
        return rentalList.stream()
                //아직 반납하지 않고 만료일이 지난 경우
                .filter(rental -> rental.getReturnDate() == null && rental.getExpiredDate().isBefore(LocalDateTime.now()))
                .map(rental -> new BookDto(rental.getBook()))
                .toList();
    }

    /**
     * 현재 대여 중인 책
     * @param userRoles
     * @return
     */
    public List<BookDto> getCurrentRentalBookCount(UserRoles userRoles) {
        userService.findAdmin(userRoles);
        //반납되지 않은 책들
        List<Rental> rentalList = rentalRepository.findByReturnDateIsNull();
        return rentalList.stream()
                .map(rental -> new BookDto(rental.getBook()))
                .toList();
    }


}
