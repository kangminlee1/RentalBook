package lee.rentalbook.domain.book.dto;

import lee.rentalbook.domain.book.Book;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long bookId;
    private String author;
    private String title;
    private String category;
    private String ISBN;

    public BookDto(Book book) {
        // 빌더를 사용하여 BookDto 객체를 설정한 후 반환하도록 수정
        this.bookId = book.getBookId();
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.category = book.getCategory();
        this.ISBN = book.getISBN();
    }

    public static BookDto of(Book book){
        return new BookDto(book);
    }

}
