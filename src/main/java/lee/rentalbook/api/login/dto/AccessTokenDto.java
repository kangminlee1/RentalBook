package lee.rentalbook.api.login.dto;

import jakarta.validation.constraints.NotBlank;

public class AccessTokenDto {
    @NotBlank
    private String accessToken;
}
