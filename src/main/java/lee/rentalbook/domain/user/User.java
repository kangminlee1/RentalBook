package lee.rentalbook.domain.user;

import jakarta.persistence.*;
import lee.rentalbook.domain.cart.Cart;
import lee.rentalbook.domain.order.Orders;
import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.domain.user.enums.UserRoles;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles roles;
    @Column(nullable = false, unique = true)
    private String userKey;
    @Column(nullable = false)
//    @CreationTimestamp -> 다른 DB 에서는 인식 못할 수도 있다함
    @CreatedDate
    private LocalDateTime createDate;
    //    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Embedded
    private UserDetails userDetails;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private UserDetails userDetails;

    @OneToMany(mappedBy = "user")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

//
//    public List<String> getRoleList() {
//        return Collections.singletonList(this.roles.name());
//    }
}
