package de.grilborzer.rvcamper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExtraServiceToBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ExtraServiceType serviceType;
    @ManyToOne
    @Transient
    @JsonIgnore
    private Booking booking;
}

