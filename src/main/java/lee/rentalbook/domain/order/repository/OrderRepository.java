package lee.rentalbook.domain.order.repository;

import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.repository.querydsl.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {
}
