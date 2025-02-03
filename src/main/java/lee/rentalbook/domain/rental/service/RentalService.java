package lee.rentalbook.domain.rental.service;

import lee.rentalbook.domain.rental.Rental;
import lee.rentalbook.domain.rental.repository.RentalRepository;
import lee.rentalbook.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @file RentalService
 * @author Kangminlee
 * @date 2025-01-13
 * @date 2025-01-17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;

    //사용자
    /**
     * 대여한 책들 찾기
     * @param userKey
     */
    public List<Rental> findRental(String userKey) {
        userService.findByUserKey(userKey);
        return rentalRepository.findByUser(userKey);
    }
    //관리자
}
