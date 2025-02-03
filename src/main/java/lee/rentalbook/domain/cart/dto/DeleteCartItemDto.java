package lee.rentalbook.domain.cart.dto;

import lombok.Data;

@Data
public class DeleteCartItemDto {
    private Long cartItemId;
    private Long bookDetailsId;
}
