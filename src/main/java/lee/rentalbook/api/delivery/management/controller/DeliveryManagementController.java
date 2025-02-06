package lee.rentalbook.api.delivery.management.controller;

import lee.rentalbook.api.delivery.management.service.DeliveryManagementService;
import lee.rentalbook.domain.delivery.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery/management")
public class DeliveryManagementController {

    private final DeliveryManagementService  deliveryManagementService;

    @PostMapping("/add")
    public ResponseEntity<Object> createDelivery(
            @AuthenticationPrincipal UserDetails userDetails,
            DeliveryDto deliveryDto
            ){

        deliveryManagementService.createDelivery(userDetails.getUsername(), deliveryDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/modfiy/info")
    public ResponseEntity<Object> modifyDeliveryInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            DeliveryDto deliveryDto
    ){
        deliveryManagementService.modifyDeliveryInfo(userDetails.getUsername(), deliveryDto);
        return ResponseEntity.ok().body(null);
    }

    //배송 상태 부분은 좀 더 생각해보자....

}
