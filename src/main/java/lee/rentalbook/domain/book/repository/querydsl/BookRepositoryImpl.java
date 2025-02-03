package lee.rentalbook.domain.book.repository.querydsl;

import com.querydsl.core.Query;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.book.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static lee.rentalbook.domain.book.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookDto> findByBookWithTitle(String title, Pageable pageable) {

        String processedTitle = "%" + title + "%";

        List<BookDto> bookDtoList = queryFactory
                .select(Projections.constructor(BookDto.class,
                        book.bookId,
                        book.author,
                        book.title,
                        book.category,
                        book.ISBN
                        ))
                .from(book)
                .where(book.title.like(processedTitle))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(book.count())
                .from(book)
                .where(book.title.like(processedTitle));

        return PageableExecutionUtils.getPage(bookDtoList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<BookDto> findByBookWithCategory(String category, Pageable pageable) {
        String processedCategory = "%" + category + "%";

        List<BookDto> bookDtoList = queryFactory
                .select(Projections.constructor(BookDto.class,
                        book.bookId,
                        book.author,
                        book.title,
                        book.category,
                        book.ISBN))
                .from(book)
                .where(book.category.like(processedCategory))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(book.count())
                .from(book)
                .where(book.category.like(processedCategory));

        return PageableExecutionUtils.getPage(bookDtoList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<BookDto> findByBookWithAuthor(String author, Pageable pageable) {
        String processedAuthor = "%" + author + "%";

        List<BookDto> bookDtoList = queryFactory
                .select(Projections.constructor(BookDto.class,
                        book.bookId,
                        book.author,
                        book.title,
                        book.category,
                        book.ISBN))
                .from(book)
                .where(book.author.like(processedAuthor))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(book.count())
                .from(book)
                .where(book.author.like(processedAuthor));
        return PageableExecutionUtils.getPage(bookDtoList, pageable, countQuery::fetchOne);
    }
}
