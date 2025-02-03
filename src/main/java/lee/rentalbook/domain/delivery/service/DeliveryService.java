package lee.rentalbook.domain.delivery.service;

import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.delivery.repository.DeliveryRepository;
import lee.rentalbook.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static lee.rentalbook.global.exception.ErrorCode.DELIVERY_NOT_FOUND;

/**
 * @file DeliveryService.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    /**
     * 배송 찾기
     * @param ordersId
     * @return
     */
    public Delivery findDelivery(Long ordersId) {
        return deliveryRepository.findByOrdersId(ordersId)
                .orElseThrow(() -> new CustomException(DELIVERY_NOT_FOUND));
    }
}
