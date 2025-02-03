package lee.rentalbook.domain.rental.repository;

import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.domain.rental.repository.querydsl.RentalRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    List<Rental> findByReturnDateIsNull();
//    List<Rental> findByReturnDateIsNotNull();
}
