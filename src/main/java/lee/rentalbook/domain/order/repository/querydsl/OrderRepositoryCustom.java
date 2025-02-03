package lee.rentalbook.domain.order.repository.querydsl;

import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryCustom {
//    @Query("select o from Orders o join o.user u where u.userKey = :userKey")
    Optional<Orders> findByUserKey(String userKey);
//    @Query("select o from Orders o join o.user u where u.roles = :userRoles")
    List<Orders> findAllByUserRoles(UserRoles userRoles);
//    @Query("select new lee.rentalbook.domain.order.dto.OrderDto(o.cart.cartId, ) " +
//            "from Orders o join fetch o.cart c where c.user.userId = :userId and c.cartId = :cartId")
    Page<OrdersDto> findByUserIdAndCartId(@Param("userId") Long userId, @Param("cartId") Long cartId, Pageable pageable);
}
