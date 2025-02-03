package lee.rentalbook.domain.book;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lee.rentalbook.domain.cart.CartItem;
import lee.rentalbook.domain.order.OrderItem;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String ISBN;
    //    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createDate;

    @OneToOne(mappedBy = "book", cascade =  CascadeType.ALL)
    private BookDetails bookDetails;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "cart_id")
//    private Cart cart;

    @OneToMany(mappedBy = "book")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "book")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "book")
    private List<OrderItem> orderItems;

    public void modifyBook(BookDetailsDto bookDetailsDto) {
        this.title = bookDetailsDto.getTitle();
        this.author = bookDetailsDto.getAuthor();
        this.category = bookDetailsDto.getCategory();
        this.ISBN = bookDetailsDto.getISBN();
    }
}
