package de.grilborzer.rvcamper.model;

import java.time.LocalDate;
import java.util.List;

public record Booking(
        String id,
        LocalDate startDate,
        LocalDate endDate,
        CampingSpace campingSpace,
        List<ExtraService> extraServices
) {
}
