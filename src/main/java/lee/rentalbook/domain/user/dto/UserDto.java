package lee.rentalbook.domain.user.dto;

import lee.rentalbook.domain.rental.Rental;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public UserDto(Rental rental) {
        this.name = rental.getUser().getName();
        this.address = rental.getUser().getUserDetails().getAddress();
        this.phoneNumber = rental.getUser().getUserDetails().getPhoneNumber();
        this.email = rental.getUser().getEmail();
        this.createDate = rental.getUser().getCreateDate();
        this.updateDate = rental.getUser().getUpdateDate();
    }


}
