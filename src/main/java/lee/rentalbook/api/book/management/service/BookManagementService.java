package lee.rentalbook.api.book.management.service;

import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.book.repository.BookDetailsRepository;
import lee.rentalbook.domain.book.service.BookService;
import lee.rentalbook.domain.user.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @file BookManagementService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class BookManagementService {

    private final BookService bookService;
    private final BookDetailsRepository bookDetailsRepository;

    //관리자

    /**
     * 책 추가
     * @param userRoles
     * @param bookDetailsDto
     */
    public void createBook(UserRoles userRoles, BookDetailsDto bookDetailsDto) {
        //userRoles 가 ADMIN 인지 확인

        //책 생성
        bookDetailsRepository.save(bookDetailsDto.toEntity());
    }

    /**
     * 책 정보 수정
     * @param bookDetailsId
     * @param userRoles
     * @param bookDetailsDto
     */
    @Transactional
    public void modifyBook(Long bookDetailsId, UserRoles userRoles, BookDetailsDto bookDetailsDto) {
        //관리자인지 확인

        //존재하는 책 번호인지 확인
        BookDetails findBookDetails = bookService.findBook(bookDetailsId);
        //수정
        findBookDetails.modifyBookDetails(bookDetailsDto);
    }

//    /**
//     * 책 삭제
//     * @param userRoles
//     * @param bookDetailsId
//     */
//    @Transactional
//    public void deleteBook(UserRoles userRoles, Long bookDetailsId) {
//        //관리자인지 확인
//
//        //책이 존재하는 지 확인
//        BookDetails findBookDetails = bookService.findBook(bookDetailsId);
//
//        //관련된 값들 삭제 및 해당 책을 장바구니에 담은 사람에게는 값이 사라져야함
//        //book까지는 CASCADE해도 될듯
//        bookDetailsRepository.delete(findBookDetails);
//    }
}
