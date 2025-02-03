package lee.rentalbook.domain.delivery.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.delivery.QDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static lee.rentalbook.domain.delivery.QDelivery.delivery;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Delivery> findByOrdersId(Long ordersId) {
        Delivery result = queryFactory
                .select(delivery)
                .from(delivery)
                .where(delivery.orders.ordersId.eq(ordersId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
