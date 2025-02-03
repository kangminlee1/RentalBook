package lee.rentalbook.api.cart.management.service;

import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.book.service.BookService;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.cart.dto.CartItemDto;
import lee.rentalbook.domain.cart.dto.DeleteCartItemDto;
import lee.rentalbook.domain.cart.repository.CartItemRepository;
import lee.rentalbook.domain.cart.repository.CartRepository;
import lee.rentalbook.domain.cart.service.CartService;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @file CartManagementService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class CartManagementService {

    private final BookService bookService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartService cartService;

    /**
     * 카트에 담기
     * @param userKey
     * @param cartItemDto
     */
    public void addCart(String userKey, CartItemDto cartItemDto) {
        //해당 상품 존재하는지 검증
        BookDetails bookDetails = bookService.findBook(cartItemDto.getBookDetailsId());
        //사용자 카트 가져오기
        Cart cart = cartRepository.findByUserKey(userKey)
                .orElseGet(() -> cartRepository.save(new Cart(userService.findByUserKey(userKey))));
        //같은 상품이 이미 카트에 존재하는지 확인
        Optional<CartItem> isItemExists = cartItemRepository.findByCartAndBook(cart, bookDetails.getBook());
        //이미 있으면 수량 증가 없으면 새로운 상품 카트에 추가
        CartItem cartItem;
        if (isItemExists.isEmpty()) {
            cartItem = CartItem.builder()
                    .quantity(cartItemDto.getQuantity())
                    .price(cartItemDto.getPrice())
                    .cart(cart)
                    .book(bookDetails.getBook())
                    .build();
        } else {
            //기존 상품에 새 정보 추가
            cartItem = isItemExists.get();
            cartItem.modifyQuantity(cartItemDto.getQuantity());
        }
        cartItemRepository.save(cartItem);
    }

    /**
     * 카트 상품 제거
     * @param userKey
     * @param deleteCartItemDtoList
     */
    @Transactional
    public void deleteParticularCartItem(String userKey, List<DeleteCartItemDto> deleteCartItemDtoList) {
        //사용자 검증 및 사용자의 장바구니 가져오기
        User user = userService.findByUserKey(userKey);
        cartService.findCart(userKey);

        for (DeleteCartItemDto deleteCartItemDto : deleteCartItemDtoList) {
            cartItemRepository.delete(cartService.findCartItem(deleteCartItemDto.getCartItemId()));
        }
    }

    /**
     * 카트 전체 삭제
     * @param userKey
     */
    //전체 삭제
    @Transactional
    public void deleteAllCartItem(String userKey) {
        Cart cart = cartService.findCart(userKey);
        //해당 장바구니 전체 삭제
        cartRepository.delete(cart);
    }

    /**
     * 수량 수정
     * @param userKey
     * @param cartItemId
     * @param quantity
     */
    @Transactional
    public void modifyCartItemQuantity(String userKey, Long cartItemId, int quantity) {
//        Cart cart = findCart(userKey);
        CartItem cartItem = cartService.findCartItem(cartItemId);
        cartItem.modifyQuantity(quantity);
    }
}
