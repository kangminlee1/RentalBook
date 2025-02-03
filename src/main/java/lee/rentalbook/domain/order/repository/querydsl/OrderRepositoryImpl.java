package lee.rentalbook.domain.order.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.cart.QCart;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.QOrders;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static lee.rentalbook.domain.cart.QCart.cart;
import static lee.rentalbook.domain.order.QOrderItem.orderItem;
import static lee.rentalbook.domain.order.QOrders.orders;
import static lee.rentalbook.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Orders> findByUserKey(String userKey) {
        Orders result = queryFactory
                .select(orders)
                .from(orders)
                .join(orders.user, user)
                .where(user.userKey.eq(userKey))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Orders> findAllByUserRoles(UserRoles userRoles) {
        return queryFactory
                .select(orders)
                .from(orders)
                .join(orders.user, user)
                .where(user.roles.eq(userRoles))
                .fetch();
    }

    @Override
    public Page<OrdersDto> findByUserIdAndCartId(Long userId, Long cartId, Pageable pageable) {
        List<OrdersDto> ordersDtoList = queryFactory
                .select(Projections.constructor(OrdersDto.class,
                        orders.cart.cartId,
                        orderItem.book.title,
                        orderItem.book.ISBN,
                        orderItem.book.author,
                        orderItem.price,
                        orderItem.quantity
                        ))
                .from(orders)
                .join(orders.cart, cart).fetchJoin()
                .where(cart.user.userId.eq(userId), cart.cartId.eq(cartId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(orders.count())
                .from(orders)
                .join(orders.cart, cart)
                .where(cart.user.userId.eq(userId), cart.cartId.eq(cartId));

        return PageableExecutionUtils.getPage(ordersDtoList, pageable, countQuery::fetchOne);
    }
}
