package lee.rentalbook.domain.rental.repository.querydsl;

import lee.rentalbook.api.rental.inquiry.dto.OverDueDetailsDto;
import lee.rentalbook.api.rental.inquiry.dto.RentalBookDto;
import lee.rentalbook.domain.rental.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentalRepositoryCustom {

//    @Query("select r from Rental r join fetch r.user u where u.userKey = :userKey")
    List<Rental> findByUser(@Param("userKey") String userKey);
//    @Query("select count(r) from Rental r join r.user u where u.userKey = :userKey and r.returnDate IS NULL")
    Long countBooksRentalByUser(@Param("userKey") String userKey);
//    @Query("select r from Rental r join fetch r.user u join fetch r.book b " +
//            "where u.userKey = :userKey and r.book.bookId = :bookId " +
//            "and r.returnDate IS NULL")
    Optional<Rental> returnedBook(@Param("userKey")String userKey, @Param("bookId") Long bookId);
//    @Query("select r from Rental r where r.user.userKey = :userKey and r.book.bookId = :bookId")
    Optional<Rental> findByUserKeyWithBookId(@Param("userKey") String userKey, @Param("bookId") Long bookId);

    //사용자 책 정보 + 연체일 + 연체료 + 반납 날짜 (없으면 null)
//    @Query("select r from Rental r join fetch r.user u where u.userKey = :userKey and r.returnDate IS NULL")
    Page<Rental> findOverDueBookListByUserKey(String userKey, Pageable pageable);
//    @Query("select new lee.rentalbook.api.rental.inquiry.dto.RentalBookDto(r.book.bookId, r.book.author, r.book.title, r.book.category, r.book.ISBN, r.returnDate) " +
//            "from Rental r join fetch r.user u where u.userId = :userId")
//    Page<RentalBookDto> findUserRentalAndOverdueByUserId(Long userId, Pageable pageable);

}
