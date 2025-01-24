package com.abcignite.test.controllertests;



import com.abcignite.test.controller.BookingsController;
import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.request.CreateBookingRequest;

import com.abcignite.test.response.CreateBookingResponse;
import com.abcignite.test.service.CreateBookingService;
import com.abcignite.test.service.SearchBookingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateBookingService createBookingService;

    @Mock
    private SearchBookingsService searchBookingsService;

    @InjectMocks
    private BookingsController bookingsController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingsController).build();
    }

    @Test
    public void testCreateBooking() throws Exception {
        // Prepare mock data
        CreateBookingRequest request = new CreateBookingRequest();
        request.setMemberName("John Doe");
        request.setBookingDate(LocalDate.now());

        CreateBookingResponse createBookingResponse = new CreateBookingResponse();
        createBookingResponse.setBookingId(UUID.randomUUID());
        createBookingResponse.setBookingDate(LocalDate.now());
        createBookingResponse.setMemberName("John Doe");
        createBookingResponse.setClassId(UUID.randomUUID());

        when(createBookingService.createBooking(request)).thenReturn(createBookingResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberName\":\"John Doe\",\"bookingDate\":\"2025-01-24\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"message\":\"Bookings created Successfully\",\"data\":{\"memberName\":\"John Doe\"}}"));
    }

}

