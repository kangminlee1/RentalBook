package lee.rentalbook.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1861772189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<lee.rentalbook.domain.cart.Cart, lee.rentalbook.domain.cart.QCart> carts = this.<lee.rentalbook.domain.cart.Cart, lee.rentalbook.domain.cart.QCart>createList("carts", lee.rentalbook.domain.cart.Cart.class, lee.rentalbook.domain.cart.QCart.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final StringPath name = createString("name");

    public final ListPath<lee.rentalbook.domain.order.Orders, lee.rentalbook.domain.order.QOrders> orders = this.<lee.rentalbook.domain.order.Orders, lee.rentalbook.domain.order.QOrders>createList("orders", lee.rentalbook.domain.order.Orders.class, lee.rentalbook.domain.order.QOrders.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<lee.rentalbook.domain.rental.Rental, lee.rentalbook.domain.rental.QRental> rentals = this.<lee.rentalbook.domain.rental.Rental, lee.rentalbook.domain.rental.QRental>createList("rentals", lee.rentalbook.domain.rental.Rental.class, lee.rentalbook.domain.rental.QRental.class, PathInits.DIRECT2);

    public final EnumPath<lee.rentalbook.domain.user.enums.UserRoles> roles = createEnum("roles", lee.rentalbook.domain.user.enums.UserRoles.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public final QUserDetails userDetails;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userKey = createString("userKey");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userDetails = inits.isInitialized("userDetails") ? new QUserDetails(forProperty("userDetails")) : null;
    }

}

