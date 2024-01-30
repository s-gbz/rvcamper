package de.grilborzer.rvcamper.controller;

import de.grilborzer.rvcamper.model.Booking;
import de.grilborzer.rvcamper.model.ExtraServiceRequest;
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

    @GetMapping("/{checkin}/{checkout}")
    List<LocalDate> getAvailableRvSpacesBetweenDates
            (@PathVariable LocalDate checkin, @PathVariable LocalDate checkout) {
        // Repository liefert gebuchte Zeiten
        // Service liefert die Inverse davon
        return new ArrayList<>();
    }

    @PostMapping("/{checkin}/{checkout}")
    Booking createBooking(@PathVariable LocalDate checkin, @PathVariable LocalDate checkout) {
        return bookingService.createBooking(checkin, checkout);
    }

    @PutMapping("/{bookingId}")
    Booking addExtraServiceToBooking(@PathVariable Long bookingId, @RequestBody ExtraServiceRequest extraService) {
        return bookingService.addExtraServiceToBooking(bookingId, extraService.extraService());
    }
}
