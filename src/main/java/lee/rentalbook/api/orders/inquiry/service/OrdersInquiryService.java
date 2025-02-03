package lee.rentalbook.api.orders.inquiry.service;

import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.service.CartService;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lee.rentalbook.domain.order.repository.OrderRepository;
import lee.rentalbook.domain.order.service.OrderService;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @file OrdersInquiryService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class OrdersInquiryService {

    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    /**
     * 주문 정보 조회
     * @param userKey
     * @return
     */
    public List<OrdersDto> getOrdersInfo(String userKey) {
        userService.findByUserKey(userKey);
        Orders orders = orderService.findOrders(userKey);

        return orders.getOrderItems().stream()
                .map(orderItem -> OrdersDto.builder()
                        .title(orderItem.getBook().getTitle())
                        .ISBN(orderItem.getBook().getISBN())
                        .author(orderItem.getBook().getAuthor())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build()
                )
                .collect(Collectors.toList());
    }

    //관리자
    /**
     * 각 상룡자의 주문 목록 조회 -> 사용자 목록에서 특정 사용자 클릭 시 수행
     * @param userRoles
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public Page<OrdersDto> getUserOrderList(UserRoles userRoles, Long userId, int page, int size) {
        //관리자인지 검증
        userService.findAdmin(userRoles);
        //해당 사용자 찾기
        User findUser = userService.findByUserId(userId);
        //그 사용자의 주문 목록 조회
        Cart cart = cartService.findCartToId(findUser.getUserId());
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByUserIdAndCartId(findUser.getUserId(), cart.getCartId(), pageable);
    }
}
