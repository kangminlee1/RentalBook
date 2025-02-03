package lee.rentalbook.api.delivery.management.service;

import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.cart.repository.CartRepository;
import lee.rentalbook.domain.cart.service.CartService;
import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.delivery.dto.DeliveryDto;
import lee.rentalbook.domain.delivery.enums.DeliveryStatus;
import lee.rentalbook.domain.delivery.repository.DeliveryRepository;
import lee.rentalbook.domain.delivery.service.DeliveryService;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.service.OrderService;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @file DeliveryManagementService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class DeliveryManagementService {

    private final UserService userService;
    private final OrderService orderService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryService deliveryService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    /**
     * 배송 저장
     * @param userKey
     * @param deliveryDto
     */
    public void createDelivery(String userKey, DeliveryDto deliveryDto) {
        userService.findByUserKey(userKey);
        Orders orders = orderService.findOrders(userKey);

        Delivery delivery = Delivery.builder()
                .address(deliveryDto.getAddress())
                .deliveryMessage(deliveryDto.getDeliveryMessage())
                .recipientName(deliveryDto.getRecipientName())
                .recipientPhoneNumber(deliveryDto.getRecipientPhoneNumber())
                .deliveryStatus(DeliveryStatus.READY)
                .orders(orders)
                .build();
        deliveryRepository.save(delivery);
    }

    /**
     * 배송 정보 수정 - DeliveryStatus 가 READY일 떄만 가능
     * @param userKey
     * @param deliveryDto
     */
    @Transactional
    public void modifyDeliveryInfo(String userKey, DeliveryDto deliveryDto){
        //본인 확인
        User user = userService.findByUserKey(userKey);
        //내 배송이 맞는지 확인
        Orders orders = orderService.findOrders(userKey);
        Delivery delivery = deliveryService.findDelivery(orders.getOrdersId());

        //배송 상태 확인
        if (isDeliveryStatusReady(orders.getOrdersId())) {
            delivery.modifyDelivery(deliveryDto);
        }else{
            throw new CustomException(ErrorCode.DELIVERY_IS_NOT_READY);
        }
    }

    /**
     * 현재 배송 상태 확인
     * @param ordersId
     * @return
     */
    public boolean isDeliveryStatusReady(Long ordersId) {
        Delivery delivery = deliveryService.findDelivery(ordersId);
        return delivery.getDeliveryStatus() == DeliveryStatus.READY;
    }

    /**
     * 배송 상태 변경
     * @param userKey
     * @param ordersId
     * @param deliveryStatus
     */
    @Transactional
    public void changeDeliveryStatus(String userKey, Long ordersId, DeliveryStatus deliveryStatus) {
        userService.findByUserKey(userKey);

        Delivery delivery = deliveryService.findDelivery(ordersId);
        delivery.modifyDeliveryStatus(deliveryStatus);
    }
    //배송 취소 (반품) -> 장바구니로 다시 값 돌려 넣어야함
    //현재는 반품 되면 갯수나 환불 처리 이런거만 바로 수행되게 개발
    public void cancelDelivery(String userKey, Long orderId) {
        userService.findByUserKey(userKey);
        //사용자의 배송 찾기
        Delivery delivery = deliveryService.findDelivery(orderId);
        //사용자의 카트 찾기
        Cart cart = cartService.findCart(userKey);

        //배송했던 주문을 cart 에 추가
        delivery.getOrders().getOrderItems().forEach(orderItem -> {
            //cartItem 찾아서
            CartItem findCartItem = cartService.findByCartAndBook(cart, orderItem.getBook());
            //있으면 수량 추가
            if(findCartItem != null){
                findCartItem.modifyQuantity(orderItem.getQuantity());
            }else{
                //없으면 추가
                CartItem cartItem = CartItem.builder()
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .cart(cart)
                        .book(orderItem.getBook())
                        .build();
            }
        });
        delivery.modifyDeliveryStatus(DeliveryStatus.CANCEL);
        cartRepository.save(cart);
    }
}
