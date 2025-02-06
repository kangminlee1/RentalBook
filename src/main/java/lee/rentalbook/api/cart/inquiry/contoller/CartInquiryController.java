package lee.rentalbook.api.cart.inquiry.contoller;

import lee.rentalbook.api.cart.inquiry.service.CartInquiryService;
import lee.rentalbook.domain.cart.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/inquiry")
public class CartInquiryController {

    private final CartInquiryService cartInquiryService;

    @GetMapping("cartList")
    public ResponseEntity<Map<String, Object>> getCartList(
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        List<CartDto> cartDtoList = cartInquiryService.getCartList(userDetails.getUsername());
        int totalPrice = cartInquiryService.calcTotalPrice(cartDtoList);

        Map<String, Object> response = new HashMap<>();
        response.put("cartList", cartDtoList);
        response.put("totalPrice", totalPrice);
        return ResponseEntity.ok(response);
    }
}
