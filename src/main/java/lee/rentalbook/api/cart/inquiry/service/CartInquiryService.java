package lee.rentalbook.api.cart.inquiry.service;

import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.dto.CartDto;
import lee.rentalbook.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @file CartInquiryService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class CartInquiryService {

    private final CartService cartService;

    /**
     * 장바구니 품목 조회
     * @param userKey
     * @return
     */
    public List<CartDto> getCartList(String userKey) {
        Cart cart = cartService.findCart(userKey);
        return cart.getCartItems().stream()
                .map(cartItem -> CartDto.builder()
                        .title(cartItem.getBook().getTitle())
                        .ISBN(cartItem.getBook().getISBN())
                        .author(cartItem.getBook().getAuthor())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getPrice())
                        .totalPrice(cartItem.getPrice() * cartItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 전체 결제 금액
     * @param cartDtoList
     * @return
     */
    public int calcTotalPrice(List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .mapToInt(CartDto::getTotalPrice)
                .sum();
    }
}
