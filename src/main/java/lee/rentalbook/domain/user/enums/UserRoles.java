package lee.rentalbook.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoles {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
    private final String value;
}
