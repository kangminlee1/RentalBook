package lee.rentalbook.api.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lee.rentalbook.domain.user.User;
import lee.rentalbook.domain.user.UserDetails;

import lee.rentalbook.domain.user.enums.UserRoles;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterDto {

//    @NotBlank(message = "아이디는 필수입니다.")
//    @Size(min = 4, max = 20, message = "사용자 아이디는 4자 이상 20자 이하입니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,20}$", message = "아이디는 영어와 숫자를 섞어 4자리 이상 20자 이하으로 입력해야 합니다.")
//    private String userId;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 최소 8자 이상 20자 이하이어야 하며, 소문자, 숫자, 특수문자가 포함되어야 합니다.")
    private String password;
    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣 ]*$", message = "이름은 영어, 한글, 공백만 포함할 수 있습니다.")
    private String name;
    @NotBlank(message = "주소는 필수입니다.")
    @Size(max = 50, message = "주소는 50자 이내로 입력해주세요.")
    private String address;
    @NotBlank(message = "전화번호는 필수 입력입니다.")  // 공백 허용하지 않음
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1111-1111)")
    private String phoneNumber;

    @Builder
    public User toEntity(String encryptedPassword) {
        UserDetails userDetails = UserDetails.builder()
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .build();

        return User.builder()
                .name(this.name)
                .roles(UserRoles.USER)
                .password(encryptedPassword)
                .userDetails(userDetails)
                .build();
    }


}
