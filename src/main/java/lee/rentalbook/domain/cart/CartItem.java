package lee.rentalbook.domain.cart;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    private int quantity;
    private int price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void modifyQuantity(int quantity) {
        this.quantity += quantity;
    }
}