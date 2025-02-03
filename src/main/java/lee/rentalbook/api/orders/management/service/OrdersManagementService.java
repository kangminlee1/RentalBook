package lee.rentalbook.api.orders.management.service;

import lee.rentalbook.api.cart.management.service.CartManagementService;
import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.cart.service.CartService;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lee.rentalbook.domain.order.repository.OrderRepository;
import lee.rentalbook.domain.order.service.OrderService;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static lee.rentalbook.global.exception.ErrorCode.QUANTITY_IS_EMPTY;

/**
 * @file OrdersManagementService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-20
 */
@Service
@RequiredArgsConstructor
public class OrdersManagementService {

    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartManagementService cartManagementService;
    private final OrderService orderService;

    /**
     * 주문 생성 시 book 수량 수정
     * @param cartItem
     */
    @Transactional
    public void reduceBookQuantity(CartItem cartItem) {
        BookDetails bookDetails = cartItem.getBook().getBookDetails();
        int quantity = bookDetails.getQuantity() - cartItem.getQuantity();
        if (quantity < 0) {
            throw new CustomException(QUANTITY_IS_EMPTY);
        } else {
            bookDetails.reduceQuantity(quantity);
        }
    }

    /**
     * 주문 취소 시 수량 롤백
     * @param cartItem
     */
    @Transactional
    public void restoredBookQuantity(CartItem cartItem) {
        BookDetails bookDetails = cartItem.getBook().getBookDetails();
        bookDetails.restoredQuantity(cartItem.getQuantity());
    }

    /**
     * 주문 생성
     * @param userKey
     * @param ordersDtoList
     */
    @Transactional
    public void createOrder(String userKey, List<OrdersDto> ordersDtoList) {
        User user = userService.findByUserKey(userKey);
        Cart cart = cartService.findCart(userKey);

        List<Orders> ordersList = ordersDtoList.stream()
                .map(ordersDto -> {
                    CartItem cartItem = cart.getCartItems().stream()
                            .filter(item -> item.getCartItemId().equals(ordersDto.getCartItemId()))
                            .findFirst()
                            .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));
                    reduceBookQuantity(cartItem);
                    return ordersDto.toEntity(cartItem);
                }).toList();

        orderRepository.saveAll(ordersList);
        cartManagementService.deleteAllCartItem(userKey);
    }

    /**
     * 주문 취소 -> 재고 복원 및 환불, 장바구니 값 돌려놓기
     * 환불은 추후 배송 관련 API 알아보고 추가하자
     * @param userKey
     */
    @Transactional
    public void cancelOrders(String userKey){
        userService.findByUserKey(userKey);
        Cart cart = cartService.findCart(userKey);

        //주문에 있는 값 장바구니로
        Orders orders = orderService.findOrders(userKey);

        //주문된 정보를 가져와서 -> 재고 복원
//        orders.getOrderItems().forEach(orderItem -> {
//            orderItem.getBook().getCartItems().forEach(cartItem -> {
//                restoredBookQuantity(cartItem);
//
//            });
//        });
        orders.getOrderItems().stream()
                .flatMap(orderItem -> orderItem.getBook().getCartItems().stream())
                .forEach(cartItem -> {
                    restoredBookQuantity(cartItem);
                    restoredItemToCart(cartItem, cart);
                });

        //주문 취소
        orderRepository.delete(orders);
    }

    /**
     * 카트 값 복원
     * @param cartItem
     * @param cart
     */
    @Transactional
    private void restoredItemToCart(CartItem cartItem, Cart cart) {
        Optional<CartItem> existItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().equals(cartItem.getBook()))
                .findFirst();

        existItem.ifPresentOrElse(
                restoredCartItem -> restoredCartItem.modifyQuantity(cartItem.getQuantity()),
                () -> cart.addCartItems(cartItem)
        );
    }
}
