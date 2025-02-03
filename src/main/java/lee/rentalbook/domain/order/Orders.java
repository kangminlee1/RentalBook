package lee.rentalbook.domain.order;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.delivery.Delivery;
import lee.rentalbook.domain.order.enums.OrderStatus;
import lee.rentalbook.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long ordersId;
    @CreatedDate
    private LocalDateTime orderDate;
    @Column(nullable = false)
    private int price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

//    @OneToMany(mappedBy = "orders")
//    private List<Book> books;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "orders")
    private Delivery delivery;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void PrePersist() {
        orderDate = LocalDateTime.now();
    }
}
