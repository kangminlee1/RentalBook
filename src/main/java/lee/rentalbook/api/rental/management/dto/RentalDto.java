package lee.rentalbook.api.rental.management.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RentalDto {
    private LocalDateTime expiredDate;
    private Long bookId;
}
