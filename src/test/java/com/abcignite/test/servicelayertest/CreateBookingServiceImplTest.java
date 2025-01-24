package com.abcignite.test.servicelayertest;


import com.abcignite.test.entity.Booking;
import com.abcignite.test.mapper.BookingsMapper;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.CreateBookingResponse;
import com.abcignite.test.service.CreateBookingService;
import com.abcignite.test.serviceimpl.CreateBookingServiceImpl;
import com.abcignite.test.validations.service.BookingValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateBookingServiceImplTest {

    private CreateBookingService createBookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingsMapper bookingsMapper;

    @Mock
    private BookingValidationService bookingValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createBookingService = new CreateBookingServiceImpl(bookingRepository, bookingsMapper, bookingValidationService);
    }

    @Test
    void createBooking_validRequest_createsBookingSuccessfully() {
        // Arrange
        CreateBookingRequest request = new CreateBookingRequest();
        request.setClassId(UUID.randomUUID());
        request.setMemberName("John Doe");
        request.setBookingDate(LocalDate.now().plusDays(1));

        Booking booking = new Booking();
        CreateBookingResponse response = new CreateBookingResponse();

        when(bookingsMapper.mapBookingRequestToBooking(request)).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingsMapper.mapBookingToCreateBookingResponse(booking)).thenReturn(response);

        // Act
        CreateBookingResponse result = createBookingService.createBooking(request);

        // Assert
        assertNotNull(result);
        verify(bookingValidationService).validateBookingRequest(request);
        verify(bookingsMapper).mapBookingRequestToBooking(request);
        verify(bookingRepository).save(any(Booking.class));
        verify(bookingsMapper).mapBookingToCreateBookingResponse(booking);
    }

    @Test
    void createBooking_validationFails_throwsException() {
        // Arrange
        CreateBookingRequest request = new CreateBookingRequest();
        request.setClassId(UUID.randomUUID());
        request.setMemberName("John Doe");
        request.setBookingDate(LocalDate.now().plusDays(1));

        doThrow(new RuntimeException("Validation failed")).when(bookingValidationService).validateBookingRequest(request);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> createBookingService.createBooking(request));
        verify(bookingValidationService).validateBookingRequest(request);
    }
}

