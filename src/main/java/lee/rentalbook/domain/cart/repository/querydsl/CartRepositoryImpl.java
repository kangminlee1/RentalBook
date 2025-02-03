package lee.rentalbook.domain.cart.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.QCart;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lee.rentalbook.domain.cart.QCart.cart;
import static lee.rentalbook.domain.user.QUser.user;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Cart> findByUserKey(String userKey) {
        Cart result = queryFactory
                .select(cart)
                .from(cart)
                .join(cart.user, user)
                .where(user.userKey.eq(userKey))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        Cart result = queryFactory
                .select(cart)
                .from(cart)
                .join(cart.user, user)
                .where(user.userId.eq(userId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
