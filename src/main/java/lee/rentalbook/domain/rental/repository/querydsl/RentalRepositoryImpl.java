package lee.rentalbook.domain.rental.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.api.rental.inquiry.dto.OverDueDetailsDto;
import lee.rentalbook.api.rental.inquiry.dto.RentalBookDto;
import lee.rentalbook.domain.rental.QRental;
import lee.rentalbook.domain.rental.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static lee.rentalbook.domain.book.QBook.book;
import static lee.rentalbook.domain.rental.QRental.rental;
import static lee.rentalbook.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class RentalRepositoryImpl implements RentalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Rental> findByUser(String userKey) {
        return queryFactory
                .select(rental)
                .from(rental)
                .join(rental.user, user).fetchJoin()
                .where(user.userKey.eq(userKey))
                .fetch();
    }

    @Override
    public Long countBooksRentalByUser(String userKey) {
        return queryFactory
                .select(rental.count())
                .from(rental)
                .join(rental.user, user)
                .where(user.userKey.eq(userKey), rental.returnDate.isNull())
                .fetchOne();
    }

    @Override
    public Optional<Rental> returnedBook(String userKey, Long bookId) {

        Rental result = queryFactory
                .select(rental)
                .from(rental)
                .join(rental.user, user).fetchJoin()
                .join(rental.book, book).fetchJoin()
                .where(user.userKey.eq(userKey), book.bookId.eq(bookId), rental.returnDate.isNull())
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Rental> findByUserKeyWithBookId(String userKey, Long bookId) {

        Rental result = queryFactory
                .select(rental)
                .from(rental)
                .where(rental.user.userKey.eq(userKey), rental.book.bookId.eq(bookId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<Rental> findOverDueBookListByUserKey(String userKey, Pageable pageable) {

        List<Rental> rentalList  = queryFactory
                .select(rental)
                .from(rental)
                .join(rental.user, user).fetchJoin()
                .where(user.userKey.eq(userKey), rental.returnDate.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //단순히 전체 갯수를 구하는거라서 fetchjoin 없음
        JPAQuery<Long> countQuery = queryFactory
                .select(rental.count())
                .from(rental)
                .join(rental.user, user)
                .where(user.userKey.eq(userKey), rental.returnDate.isNull());

        return PageableExecutionUtils.getPage(rentalList, pageable, countQuery::fetchOne);
    }
}
