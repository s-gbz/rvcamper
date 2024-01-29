package de.grilborzer.rvcamper.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    @RequestMapping("/{startDate}/{endDate}")
    List<LocalDate> getAvailableRvSpacesBetweenDates
            (@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        // Repository liefert gebuchte Zeiten
        // Service liefert die Inverse davon
        return new ArrayList<>();
    }
}
