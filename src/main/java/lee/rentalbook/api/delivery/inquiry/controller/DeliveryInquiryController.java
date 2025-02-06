package lee.rentalbook.api.delivery.inquiry.controller;

import lee.rentalbook.api.delivery.inquiry.service.DeliveryInquiryService;
import lee.rentalbook.domain.delivery.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery/inquiry")
@RequiredArgsConstructor
public class DeliveryInquiryController {

    private final DeliveryInquiryService deliveryInquiryService;

    @GetMapping("/myDelivery")
    public ResponseEntity<DeliveryDto> getMyDelivery(
            @AuthenticationPrincipal UserDetails userDetails,
            Long ordersId
            ) {
        DeliveryDto deliveryDto = deliveryInquiryService.getDeliveryInfo(userDetails.getUsername(), ordersId);
        return ResponseEntity.ok(deliveryDto);
    }

}
