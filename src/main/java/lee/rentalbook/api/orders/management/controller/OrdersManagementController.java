package lee.rentalbook.api.orders.management.controller;

import lee.rentalbook.api.orders.management.service.OrdersManagementService;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/management")
public class OrdersManagementController {

    private final OrdersManagementService ordersManagementService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "ordersDtoList") List<OrdersDto> ordersDtoList
            ){
        ordersManagementService.createOrder(userDetails.getUsername(), ordersDtoList);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Object> cancelOrders(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        ordersManagementService.cancelOrders(userDetails.getUsername());
        return ResponseEntity.ok().body(null);
    }
}
