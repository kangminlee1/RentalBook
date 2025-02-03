package lee.rentalbook.api.rental.inquiry.dto;

import lee.rentalbook.domain.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalBookDto {
    //대여 내역
    private Long bookId;
    private String author;
    private String title;
    private String category;
    private String ISBN;

    //연체 정보
    private long overDueCost;
    private long overDueDate;
//    private LocalDateTime returnDate;

    //생성자는 빌더 부적합
    public RentalBookDto(Rental rental) {
        this.bookId = rental.getBook().getBookId();
        this.ISBN = rental.getBook().getISBN();
        this.title = rental.getBook().getTitle();
        this.category = rental.getBook().getCategory();
        this.author = rental.getBook().getAuthor();
    }
    
    public void addOverdueInfo(long overDueDate, long overDueCost) {
        this.overDueDate = overDueDate;
        this.overDueCost = overDueCost;
    }
}
