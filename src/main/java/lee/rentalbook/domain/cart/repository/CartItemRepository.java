package lee.rentalbook.domain.cart.repository;

import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.cart.repository.querydsl.CartItemRepositoryCustom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long>, CartItemRepositoryCustom {
    Optional<CartItem> findByCartItemId(Long cartItemId);
}
