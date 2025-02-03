package lee.rentalbook.domain.user.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lee.rentalbook.domain.user.QUser;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static lee.rentalbook.domain.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findAdminByRoles(UserRoles roles) {

        User result = queryFactory
                .select(user)
                .from(user)
                .where(user.roles.eq(roles))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<UserDto> findByRoles(UserRoles roles, Pageable pageable) {

        List<UserDto> result = queryFactory
                .select(Projections.constructor(UserDto.class,
                        user.name,
                        user.userDetails.phoneNumber,
                        user.email,
                        user.userDetails.address,
                        user.createDate,
                        user.updateDate))
                .from(user)
                .where(user.roles.eq(roles))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(user.roles.eq(roles));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
}
