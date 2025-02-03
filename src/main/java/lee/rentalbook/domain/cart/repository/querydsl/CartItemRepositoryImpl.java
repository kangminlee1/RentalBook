package lee.rentalbook.domain.cart.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static lee.rentalbook.domain.book.QBook.book;
import static lee.rentalbook.domain.cart.QCartItem.cartItem;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CartItem> findByCartAndBook(Cart cart, Book bookInfo) {
        CartItem result = queryFactory
                .select(cartItem)
                .from(cartItem)
                .join(cartItem.book, book).fetchJoin()
                .where(cartItem.cart.eq(cart), book.eq(bookInfo))
                .fetchOne();
        return Optional.ofNullable(result);
    }

}
