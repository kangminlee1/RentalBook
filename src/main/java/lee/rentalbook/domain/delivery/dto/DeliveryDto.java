package lee.rentalbook.domain.delivery.dto;

import lee.rentalbook.domain.delivery.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryMessage;
    private String address;

    public DeliveryDto(Delivery delivery) {
        this.recipientName = delivery.getRecipientName();
        this.recipientPhoneNumber = delivery.getRecipientPhoneNumber();
        this.deliveryMessage = delivery.getDeliveryMessage();
        this.address = delivery.getAddress();
    }

    public static DeliveryDto of(Delivery delivery){
        return new DeliveryDto(delivery);
    }

}
