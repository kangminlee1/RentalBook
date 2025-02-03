package lee.rentalbook.domain.order;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    private int quantity;
    private int price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


}
