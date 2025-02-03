package lee.rentalbook.domain.order.service;

import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.repository.OrderRepository;
import lee.rentalbook.global.exception.CustomException;
import lee.rentalbook.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @file OrderService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    /**
     * 주문 찾기
     * @param userKey
     * @return
     */
    public Orders findOrders(String userKey) {
        return orderRepository.findByUserKey(userKey)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

}
