package de.grilborzer.rvcamper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public final class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;
    @OneToOne
    @JoinColumn(name = "rvSpace_id", referencedColumnName = "id")
    @JsonManagedReference
    private RvSpace rvSpace;
}
