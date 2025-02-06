package lee.rentalbook.api.cart.management.controller;

import lee.rentalbook.api.cart.management.service.CartManagementService;
import lee.rentalbook.domain.cart.dto.CartItemDto;
import lee.rentalbook.domain.cart.dto.DeleteCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/management")
@RequiredArgsConstructor
public class CartManagementController {

    private final CartManagementService cartManagementService;

    @PostMapping("/add")
    public ResponseEntity<Object> addCart(
            @AuthenticationPrincipal UserDetails userDetails
            , CartItemDto cartItemDto
            ){
        cartManagementService.addCart(userDetails.getUsername(), cartItemDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/delete/cart")
    public ResponseEntity<Object> deleteCart(
            @AuthenticationPrincipal UserDetails userDetails
            , List<DeleteCartItemDto> deleteCartItemDtoList
    ){
        cartManagementService.deleteParticularCartItem(userDetails.getUsername(), deleteCartItemDtoList);
        return ResponseEntity.ok().body(null);

    }

    @PostMapping("/delete/all")
    public ResponseEntity<Object> deleteAllCart(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        cartManagementService.deleteAllCartItem(userDetails.getUsername());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/modify")
    public ResponseEntity<Object> modifyCart(
            @AuthenticationPrincipal UserDetails userDetails,
            Long cartId,
            int quantity
    ){
        cartManagementService.modifyCartItemQuantity(userDetails.getUsername(), cartId, quantity);
        return ResponseEntity.ok().body(null);
    }
}
