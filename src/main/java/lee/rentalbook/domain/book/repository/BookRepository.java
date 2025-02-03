package lee.rentalbook.domain.book.repository;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.repository.querydsl.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
}
