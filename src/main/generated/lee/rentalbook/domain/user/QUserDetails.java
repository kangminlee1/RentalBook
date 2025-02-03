package lee.rentalbook.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserDetails is a Querydsl query type for UserDetails
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserDetails extends BeanPath<UserDetails> {

    private static final long serialVersionUID = -1399404449L;

    public static final QUserDetails userDetails = new QUserDetails("userDetails");

    public final StringPath address = createString("address");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QUserDetails(String variable) {
        super(UserDetails.class, forVariable(variable));
    }

    public QUserDetails(Path<? extends UserDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserDetails(PathMetadata metadata) {
        super(UserDetails.class, metadata);
    }

}

