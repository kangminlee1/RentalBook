package lee.rentalbook.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    COMPLETION("주문완료"), CANCEL("주문최소");

    private final String value;
}
