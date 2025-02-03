package lee.rentalbook.domain.cart.repository;

import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.repository.querydsl.CartRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long>, CartRepositoryCustom {
}
