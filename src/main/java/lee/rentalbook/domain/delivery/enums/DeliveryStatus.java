package lee.rentalbook.domain.delivery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {
    COMPLETION("배송완료"), GOING("배송중"), READY("준비중"), CANCEL("취소");

    private final String value;
}
