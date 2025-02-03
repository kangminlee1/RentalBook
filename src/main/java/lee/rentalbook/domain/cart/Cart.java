package lee.rentalbook.domain.cart;

import jakarta.persistence.*;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    //    private int totalPrice;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
//    @OneToMany(mappedBy = "cart")
//    private List<Book> books;
    @OneToOne(mappedBy = "cart")
    private Orders orders;

    @OneToMany(mappedBy = "cart", cascade = ALL)
    private List<CartItem> cartItems;

    public Cart(User user) {
        this.user = user;
    }

    public void addCartItems(CartItem cartItem) {
        if (this.cartItems == null) {
            this.cartItems = new ArrayList<>();
        }
        this.cartItems.add(cartItem);
    }
}
