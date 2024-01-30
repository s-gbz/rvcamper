package de.grilborzer.rvcamper.service;

import de.grilborzer.rvcamper.model.Booking;
import de.grilborzer.rvcamper.model.ExtraServiceToBook;
import de.grilborzer.rvcamper.model.ExtraServiceType;
import de.grilborzer.rvcamper.model.RvSpace;
import de.grilborzer.rvcamper.repository.BookingRepository;
import de.grilborzer.rvcamper.repository.RvSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RvSpaceRepository rvSpaceRepository;

    public Booking createBooking(LocalDate checkin, LocalDate checkout) {

        RvSpace savedSpace = rvSpaceRepository.save(new RvSpace());
        Booking savedBooking = bookingRepository.save(new Booking(null, checkin, checkout, savedSpace, null));
        RvSpace savedSpaceWithBookingIdSet = new RvSpace(savedSpace.getId(), savedBooking);

        rvSpaceRepository.save(savedSpaceWithBookingIdSet);

        return savedBooking;
    }

    public Booking addExtraServiceToBooking(Long bookingId, ExtraServiceType extraServiceType) {
        var targetBooking = bookingRepository.findById(bookingId).orElseThrow(RuntimeException::new);

        return bookingRepository.save(
                targetBooking.withExtraServices(
                        List.of(new ExtraServiceToBook(null, extraServiceType, targetBooking))
                ));
    }
}
