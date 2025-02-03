package lee.rentalbook.api.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WithdrawDto {

    @NotBlank(message = "사용자 이메일 필수입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
