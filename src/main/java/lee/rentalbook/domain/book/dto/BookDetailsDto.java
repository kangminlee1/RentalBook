package lee.rentalbook.domain.book.dto;

import lee.rentalbook.domain.book.Book;
import lee.rentalbook.domain.book.BookDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {

    private String author;
    private String title;
    private String publisher;
    private String category;
    private String ISBN;
    private int quantity;
    private int price;
    private String description;

    public BookDetails toEntity() {
        Book book = Book.builder()
                .author(this.author)
                .category(this.category)
                .title(this.title)
                .ISBN(this.ISBN)
                .build();
        return BookDetails.builder()
                .quantity(this.quantity)
                .rentalQuantity(0)
                .price(this.price)
                .publisher(this.publisher)
                .description(this.description)
                .book(book)
                .build();
    }
    public BookDetailsDto(BookDetails bookDetails) {
        this.author = bookDetails.getBook().getAuthor();
        this.title = bookDetails.getBook().getTitle();
        this.category = bookDetails.getBook().getCategory();
        this.ISBN = bookDetails.getBook().getISBN();
        this.description = bookDetails.getDescription();
        this.price = bookDetails.getPrice();
        this.publisher = bookDetails.getPublisher();
        this.quantity = bookDetails.getQuantity();
    }
    public static BookDetailsDto of(BookDetails bookDetails) {
        return new BookDetailsDto(bookDetails);
    }
}
