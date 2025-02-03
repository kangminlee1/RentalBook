package lee.rentalbook.domain.book;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.dto.BookDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_details_id")
    private Long bookDetailsId;
    @Column(nullable = false)
    private int quantity;

    private int rentalQuantity;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void modifyBookDetails(BookDetailsDto bookDetailsDto) {
        this.quantity = bookDetailsDto.getQuantity();
        this.description = bookDetailsDto.getDescription();
        this.price = bookDetailsDto.getPrice();
        this.publisher = bookDetailsDto.getPublisher();
        this.book.modifyBook(bookDetailsDto);
    }

    //주문 관련
    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void restoredQuantity(int quantity) {
        this.quantity += quantity;
    }

    //대여 관련
    public void returnedQuantity() {
        this.quantity++;
    }
}
