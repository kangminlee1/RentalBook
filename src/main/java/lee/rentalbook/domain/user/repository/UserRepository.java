package lee.rentalbook.domain.user.repository;

import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.enums.UserRoles;
import lee.rentalbook.domain.user.dto.UserDto;
import lee.rentalbook.domain.user.repository.querydsl.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserKey(String userKey);
    Optional<User> findByUserId(Long userId);
    boolean existsByEmail(String email);
}
