package lee.rentalbook.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartDto {
    private String title;
    private String ISBN;
    private String author;
    private int price;
    private int quantity;
}
