package lee.rentalbook.domain.cart.repository.querydsl;

import lee.rentalbook.domain.cart.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepositoryCustom {
//    @Query("select c from Cart c join c.user u where u.userKey = :userKey")
    Optional<Cart> findByUserKey(@Param("userKey") String userKey);
//    @Query("select c from Cart c join c.user u where u.userId = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}
