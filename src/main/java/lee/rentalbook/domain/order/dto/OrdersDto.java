package lee.rentalbook.domain.order.dto;

import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.order.OrderItem;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
public class OrdersDto {
    private Long cartItemId;

    private String title;
    private String ISBN;
    private String author;

    private int price;
    private int quantity;


    public Orders toEntity(CartItem cartItem) {
        OrderItem orderItem = OrderItem.builder()
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice() * cartItem.getQuantity())
                .book(cartItem.getBook())
                .build();

        return Orders.builder()
                .price(orderItem.getPrice())
                .orderStatus(OrderStatus.COMPLETION)
                .orderItems(Collections.singletonList(orderItem))
                .build();
    }
}
