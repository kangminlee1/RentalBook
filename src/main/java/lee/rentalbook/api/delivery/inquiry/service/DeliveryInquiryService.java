package lee.rentalbook.api.delivery.inquiry.service;

import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.delivery.dto.DeliveryDto;
import lee.rentalbook.domain.delivery.service.DeliveryService;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @file DeliveryInquiryService.java
 * @author Kangminlee
 * @date 2025-01-13
 * @lastModified 2025-01-17
 */
@Service
@RequiredArgsConstructor
public class DeliveryInquiryService {

    private final UserService userService;
    private final DeliveryService deliveryService;

    /**
     * 배송 조회
     * @param userKey
     * @param ordersId
     * @return
     */
    public DeliveryDto getDeliveryInfo(String userKey, Long ordersId){
        userService.findByUserKey(userKey);
        Delivery delivery = deliveryService.findDelivery(ordersId);
        return DeliveryDto.of(delivery);
    }

    //배송 추적 -> 나중에 Spring, Data JPA, JWT 등 잘 다룰때 API 따와서 해보자

}
