package lee.rentalbook.api.orders.inquiry.controller;

import lee.rentalbook.api.orders.inquiry.service.OrdersInquiryService;
import lee.rentalbook.domain.order.dto.OrdersDto;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders/inquiry")
@RequiredArgsConstructor
public class OrdersInquiryController {

    private final OrdersInquiryService ordersInquiryService;
    private final UserService userService;


    @GetMapping("/list")
    public ResponseEntity<List<OrdersDto>> getOrdersList(
            @AuthenticationPrincipal UserDetails userDetails
            ){
        List<OrdersDto> ordersDtoList = ordersInquiryService.getOrdersInfo(userDetails.getUsername());
        return ResponseEntity.ok(ordersDtoList);
    }

    @GetMapping("/admin/orderList")
    public ResponseEntity<Page<OrdersDto>> getUserOrdersList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        User user = userService.findByUserKey(userDetails.getUsername());
        Page<OrdersDto> ordersDtoPage = ordersInquiryService.getUserOrderList(user.getRoles(), userId
        , page, size);
        return ResponseEntity.ok(ordersDtoPage);
    }

}
