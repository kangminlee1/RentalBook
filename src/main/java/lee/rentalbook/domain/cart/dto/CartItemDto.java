package lee.rentalbook.domain.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long bookDetailsId;
    private int price;
    private int quantity;
}

