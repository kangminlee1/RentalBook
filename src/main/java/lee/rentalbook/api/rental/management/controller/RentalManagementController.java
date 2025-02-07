package lee.rentalbook.api.rental.management.controller;

import lee.rentalbook.api.rental.management.dto.RentalDto;
import lee.rentalbook.api.rental.management.service.RentalManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental/management")
public class RentalManagementController {

    private final RentalManagementService rentalManagementService;

    @PostMapping("/create")
    public ResponseEntity<Object> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "rentalDto")RentalDto rentalDto
            ){
        rentalManagementService.createRental(userDetails.getUsername(), rentalDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/returned")
    public ResponseEntity<Object> returnedRentalBook(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "bookId") Long bookId
    ){
        rentalManagementService.returnedRentalBook(userDetails.getUsername(), bookId);
        return ResponseEntity.ok().body(null);
    }
}
