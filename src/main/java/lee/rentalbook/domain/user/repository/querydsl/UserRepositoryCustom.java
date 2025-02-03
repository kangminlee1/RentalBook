package lee.rentalbook.domain.user.repository.querydsl;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepositoryCustom {
//    @Query("select u from User u where u.roles = :roles")
    Optional<User> findAdminByRoles(@Param("roles") UserRoles roles);

//    @Query("select new lee.rentalbook.domain.user.dto.UserDto(u.name, u.userDetails.phoneNumber, u.email, u.userDetails.address, u.createDate, u.updateDate) " +
//            "from User u " +
//            "where u.roles = :roles")
    Page<UserDto> findByRoles(@Param("roles") UserRoles roles, Pageable pageable);

}
