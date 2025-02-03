package lee.rentalbook.api.rental.inquiry.dto;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.BookDetails;
import lee.rentalbook.domain.book.dto.BookDto;
import lee.rentalbook.domain.rental.Rental;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class OverDueDetailsDto extends BookDto {
    private long overDueCost;
    private long overDueDate;
//    private LocalDateTime returnDate;

    public OverDueDetailsDto(Book book, long overDueCost, long overDueDate) {
        super(book);
        this.overDueCost = overDueCost;
        this.overDueDate = overDueDate;
    }

}
