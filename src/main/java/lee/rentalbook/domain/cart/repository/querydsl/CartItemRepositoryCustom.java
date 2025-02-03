package lee.rentalbook.domain.cart.repository.querydsl;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepositoryCustom {
//    @Query("select ci from CartItem ci join fetch ci.book b " +
//            "where ci.cart = :cart and b = :book")
    Optional<CartItem> findByCartAndBook(@Param("cart") Cart cart, @Param("book") Book book);
}
