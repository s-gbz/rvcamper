package de.grilborzer.rvcamper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public final class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;
    @OneToOne(cascade = CascadeType.ALL) // Is cascade = CascadeType.ALL required or beneficial here?
    @JoinColumn(name = "rvSpace_id", referencedColumnName = "id")
    @JsonManagedReference
    private RvSpace rvSpace;
    @OneToMany(cascade = CascadeType.ALL)
    // No need to persist this field separately, because of the CascadeType
    private List<ExtraServiceToBook> extraServices;

    public Booking withExtraServices(List<ExtraServiceToBook> extraServices) {
        return new Booking(this.id, this.checkin, this.checkout, this.rvSpace, extraServices);
    }
}
