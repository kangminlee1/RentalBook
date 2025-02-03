package lee.rentalbook.domain.rental;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRental is a Querydsl query type for Rental
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRental extends EntityPathBase<Rental> {

    private static final long serialVersionUID = -1165400317L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRental rental = new QRental("rental");

    public final lee.rentalbook.domain.book.QBook book;

    public final DateTimePath<java.time.LocalDateTime> expiredDate = createDateTime("expiredDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalDate = createDateTime("rentalDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> rentalId = createNumber("rentalId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> returnDate = createDateTime("returnDate", java.time.LocalDateTime.class);

    public final lee.rentalbook.domain.user.QUser user;

    public QRental(String variable) {
        this(Rental.class, forVariable(variable), INITS);
    }

    public QRental(Path<? extends Rental> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRental(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRental(PathMetadata metadata, PathInits inits) {
        this(Rental.class, metadata, inits);
    }

    public QRental(Class<? extends Rental> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new lee.rentalbook.domain.book.QBook(forProperty("book"), inits.get("book")) : null;
        this.user = inits.isInitialized("user") ? new lee.rentalbook.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

