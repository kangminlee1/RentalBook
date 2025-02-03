package lee.rentalbook.domain.cart.service;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.service.BookService;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.cart.repository.CartItemRepository;
import lee.rentalbook.domain.cart.repository.CartRepository;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static lee.rentalbook.global.exception.ErrorCode.CART_IS_EMPTY;
import static lee.rentalbook.global.exception.ErrorCode.CART_ITEM_NOT_FOUND;

/**
 * @file CartService.java
 * @description 장바구니 서비스 클래스
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    //공통
    /**
     * 장바구니 찾기
     * @param userKey
     * @return
     */
    public Cart findCart(String userKey) {
        return cartRepository.findByUserKey(userKey)
                .orElseThrow(() -> new CustomException(CART_IS_EMPTY));
    }

    /**
     * 장바구니 찾기
     * @param userId
     * @return
     */
    public Cart findCartToId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(CART_IS_EMPTY));
    }

    /**
     * 장바구니 물건 찾기
     * @param cartItemId
     * @return
     */
    public CartItem findCartItem(Long cartItemId) {
        return cartItemRepository.findByCartItemId(cartItemId)
                .orElseThrow(() -> new CustomException(CART_ITEM_NOT_FOUND));
    }

    public CartItem findByCartAndBook(Cart cart, Book book) {
        return cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new CustomException(CART_ITEM_NOT_FOUND));
    }
}
