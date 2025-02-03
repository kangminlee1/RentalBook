package lee.rentalbook.domain.delivery;

import jakarta.persistence.*;
import lee.rentalbook.domain.delivery.dto.DeliveryDto;
import lee.rentalbook.domain.delivery.enums.DeliveryStatus;
import lee.rentalbook.domain.order.Orders;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String recipientName;//수령인
    @Column(nullable = false)
    private String recipientPhoneNumber;//수령인 연락처
    @Column(nullable = false)
    private String deliveryMessage;


    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime deliveryDate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    public void modifyDeliveryStatus(DeliveryStatus deliveryStatus){
        this.deliveryStatus = deliveryStatus;
    }

    public void modifyDelivery(DeliveryDto deliveryDto) {
        this.address = deliveryDto.getAddress();
        this.deliveryMessage = deliveryDto.getDeliveryMessage();
        this.recipientName = deliveryDto.getRecipientName();
        this.recipientPhoneNumber = deliveryDto.getRecipientName();
    }
}
