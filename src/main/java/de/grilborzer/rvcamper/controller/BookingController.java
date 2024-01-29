package de.grilborzer.rvcamper.controller;

import de.grilborzer.rvcamper.model.Booking;
import de.grilborzer.rvcamper.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/bookings")
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{startDate}/{endDate}")
    List<LocalDate> getAvailableRvSpacesBetweenDates
            (@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        // Repository liefert gebuchte Zeiten
        // Service liefert die Inverse davon
        return new ArrayList<>();
    }

    @PostMapping("/{checkin}/{checkout}")
    Booking createBooking(@PathVariable LocalDate checkin, @PathVariable LocalDate checkout) {
        return bookingService.createBooking(checkin, checkout);
    }
}
