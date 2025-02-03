package lee.rentalbook.api.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDto {
    private String name;
    private String address;
    private String phoneNumber;

}
