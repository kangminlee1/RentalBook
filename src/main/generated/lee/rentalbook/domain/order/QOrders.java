package lee.rentalbook.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 1373571408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final lee.rentalbook.domain.cart.QCart cart;

    public final lee.rentalbook.domain.delivery.QDelivery delivery;

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final EnumPath<lee.rentalbook.domain.order.enums.OrderStatus> orderStatus = createEnum("orderStatus", lee.rentalbook.domain.order.enums.OrderStatus.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final lee.rentalbook.domain.user.QUser user;

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new lee.rentalbook.domain.cart.QCart(forProperty("cart"), inits.get("cart")) : null;
        this.delivery = inits.isInitialized("delivery") ? new lee.rentalbook.domain.delivery.QDelivery(forProperty("delivery"), inits.get("delivery")) : null;
        this.user = inits.isInitialized("user") ? new lee.rentalbook.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

