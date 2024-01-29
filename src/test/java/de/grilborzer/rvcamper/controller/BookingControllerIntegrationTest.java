package de.grilborzer.rvcamper.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    // https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart#46-using-spring-boot-310-serviceconnection
    @Container
    @ServiceConnection
    static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.17-alpine3.19");

    private static final String BASE_URL = "/api/v1/bookings";

    @Test
    void getAvailableRvSpacesBetweenDates_whenNoBookingsBetweenDates_thenReturnAvailabilityForEveryday() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(get(BASE_URL + "/" + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    void getAvailableRvSpacesBetweenDates_whenAllSpacesAreBooked_thenReturnEmptyList() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(get(BASE_URL + "/" + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()
                );
    }

    @Test
    void createBooking_shouldReturnBookingWithIdsSetInBookingAndRvSpace_whenRequestIsValid() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(post(BASE_URL + "/" + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rvSpace.id").value(1L)
                );
    }
}