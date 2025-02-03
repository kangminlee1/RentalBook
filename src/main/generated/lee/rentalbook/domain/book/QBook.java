package lee.rentalbook.domain.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -177053917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBook book = new QBook("book");

    public final StringPath author = createString("author");

    public final QBookDetails bookDetails;

    public final NumberPath<Long> bookId = createNumber("bookId", Long.class);

    public final ListPath<lee.rentalbook.domain.cart.CartItem, lee.rentalbook.domain.cart.QCartItem> cartItems = this.<lee.rentalbook.domain.cart.CartItem, lee.rentalbook.domain.cart.QCartItem>createList("cartItems", lee.rentalbook.domain.cart.CartItem.class, lee.rentalbook.domain.cart.QCartItem.class, PathInits.DIRECT2);

    public final StringPath category = createString("category");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath ISBN = createString("ISBN");

    public final ListPath<lee.rentalbook.domain.order.OrderItem, lee.rentalbook.domain.order.QOrderItem> orderItems = this.<lee.rentalbook.domain.order.OrderItem, lee.rentalbook.domain.order.QOrderItem>createList("orderItems", lee.rentalbook.domain.order.OrderItem.class, lee.rentalbook.domain.order.QOrderItem.class, PathInits.DIRECT2);

    public final lee.rentalbook.domain.order.QOrders orders;

    public final ListPath<lee.rentalbook.domain.rental.Rental, lee.rentalbook.domain.rental.QRental> rentals = this.<lee.rentalbook.domain.rental.Rental, lee.rentalbook.domain.rental.QRental>createList("rentals", lee.rentalbook.domain.rental.Rental.class, lee.rentalbook.domain.rental.QRental.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QBook(String variable) {
        this(Book.class, forVariable(variable), INITS);
    }

    public QBook(Path<? extends Book> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBook(PathMetadata metadata, PathInits inits) {
        this(Book.class, metadata, inits);
    }

    public QBook(Class<? extends Book> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookDetails = inits.isInitialized("bookDetails") ? new QBookDetails(forProperty("bookDetails"), inits.get("bookDetails")) : null;
        this.orders = inits.isInitialized("orders") ? new lee.rentalbook.domain.order.QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}

