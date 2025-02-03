package lee.rentalbook.domain.delivery.repository;

import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.delivery.enums.DeliveryStatus;
import lee.rentalbook.domain.delivery.repository.querydsl.DeliveryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {
}
