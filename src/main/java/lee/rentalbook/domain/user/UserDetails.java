package lee.rentalbook.domain.user;

import jakarta.persistence.*;
import lee.rentalbook.api.account.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

//    @Column(name = "user_details_id")
//    private Long userDetailsId;
//    @Column(nullable = false) 이건 나중에 입력해도 ㅇㅋ인걸로
    private String address;
    @Column(nullable = false)
    private String phoneNumber;

//    @OneToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id", unique = true)
//    private User user;

    public void modify(ProfileDto profileDto) {
        this.address = profileDto.getAddress();
        this.phoneNumber = profileDto.getPhoneNumber();
    }

}
