package de.grilborzer.rvcamper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.grilborzer.rvcamper.model.Booking;
import de.grilborzer.rvcamper.model.ExtraServiceRequest;
import de.grilborzer.rvcamper.model.ExtraServiceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    private static final String BASE_URL = "/api/v1/bookings/";

    @Test
    void getAvailableRvSpacesBetweenDates_whenNoBookingsBetweenDates_thenReturnAvailabilityForEveryday() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(get(BASE_URL + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()
                );
    }

    @Test
    void getAvailableRvSpacesBetweenDates_whenAllSpacesAreBooked_thenReturnEmptyList() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(get(BASE_URL + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty()
                );
    }

    @Test
    void createBooking_shouldReturnBookingWithIdsSetInBookingAndRvSpace_whenRequestIsValid() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        mockMvc.perform(post(BASE_URL + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rvSpace.id").value(1L)
                );
    }

    @Test
    void addExtraServiceToBooking_shouldAddSelectedServiceToExistingBookingAndReturnUpdatedBooking_whenRequestIsValid() throws Exception {
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 8);

        MvcResult mvcResult = mockMvc.perform(post(BASE_URL + checkin + "/" + checkout))
                .andExpect(status().isOk())
                .andReturn();

        Booking firstBooking = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Booking.class);

        ExtraServiceType extraServiceType = ExtraServiceType.WIFI;

        mockMvc.perform(put(BASE_URL + firstBooking.getId()).contentType("application/json")
                        .content(objectMapper.writeValueAsString(new ExtraServiceRequest(extraServiceType))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstBooking.getId()))
                .andExpect(jsonPath("$.extraServices[0].id").isNotEmpty())
                .andExpect(jsonPath("$.extraServices[0].serviceType").value(extraServiceType.name()))
                .andExpect(jsonPath("$.extraServices[0].booking").doesNotHaveJsonPath());
    }
}