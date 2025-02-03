package lee.rentalbook.domain.book.repository;

import lee.rentalbook.domain.book.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
}
