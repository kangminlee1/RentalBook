package lee.rentalbook.domain.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookDetails is a Querydsl query type for BookDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookDetails extends EntityPathBase<BookDetails> {

    private static final long serialVersionUID = -372281953L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookDetails bookDetails = new QBookDetails("bookDetails");

    public final QBook book;

    public final NumberPath<Long> bookDetailsId = createNumber("bookDetailsId", Long.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath publisher = createString("publisher");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> rentalQuantity = createNumber("rentalQuantity", Integer.class);

    public QBookDetails(String variable) {
        this(BookDetails.class, forVariable(variable), INITS);
    }

    public QBookDetails(Path<? extends BookDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookDetails(PathMetadata metadata, PathInits inits) {
        this(BookDetails.class, metadata, inits);
    }

    public QBookDetails(Class<? extends BookDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new QBook(forProperty("book"), inits.get("book")) : null;
    }

}

