package lee.rentalbook.domain.book.repository.querydsl;

import lee.rentalbook.domain.book.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepositoryCustom {
    @Query("select new lee.rentalbook.domain.book.dto.BookDto(b.bookId, b.title, b.author, b.category, b.ISBN) " +
            "from Book b where b.title like %:title%")
    Page<BookDto> findByBookWithTitle(@Param("title") String title, Pageable pageable);

    @Query("select new lee.rentalbook.domain.book.dto.BookDto(b.bookId, b.title, b.author, b.category, b.ISBN) " +
            "from Book b where b.category like %:category%")
    Page<BookDto> findByBookWithCategory(@Param("category") String category, Pageable pageable);

    @Query("select new lee.rentalbook.domain.book.dto.BookDto(b.bookId, b.title, b.author, b.category, b.ISBN) " +
            "from Book b where b.author like %:author%")
    Page<BookDto> findByBookWithAuthor(@Param("author") String author, Pageable pageable);
}
