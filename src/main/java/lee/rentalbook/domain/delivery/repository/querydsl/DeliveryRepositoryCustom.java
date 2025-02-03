package lee.rentalbook.domain.delivery.repository.querydsl;

import lee.rentalbook.domain.delivery.Delivery;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeliveryRepositoryCustom {
//    @Query("select d from Delivery d where d.orders.ordersId = :ordersId")
    Optional<Delivery> findByOrdersId(Long ordersId);
}
