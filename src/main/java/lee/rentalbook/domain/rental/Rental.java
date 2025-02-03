package lee.rentalbook.domain.rental;

import jakarta.persistence.*;
import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long rentalId;
    @CreatedDate
    private LocalDateTime rentalDate;//대여일
    //    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime expiredDate;//만료일
    @LastModifiedDate
    private LocalDateTime returnDate;//반납일

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void updateExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void markAsReturned() {
        this.returnDate = LocalDateTime.now();
    }
}
