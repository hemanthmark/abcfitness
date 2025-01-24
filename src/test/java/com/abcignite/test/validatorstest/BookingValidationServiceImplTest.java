package com.abcignite.test.validatorstest;

import com.abcignite.test.entity.Booking;
import com.abcignite.test.entity.Class;
import com.abcignite.test.exceptions.*;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.validations.serviceimpl.BookingValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookingValidationServiceImplTest {

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private BookingValidationServiceImpl bookingValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateBookingRequest_validRequest_noExceptionThrown() {
        // Arrange
        UUID classId = UUID.randomUUID();
        LocalDate bookingDate = LocalDate.now().plusDays(1);
        CreateBookingRequest request = new CreateBookingRequest();
        request.setClassId(classId);
        request.setBookingDate(bookingDate);

        Class aClass = new Class();
        aClass.setClassId(classId);
        aClass.setCapacity(5);
        aClass.setStartDate(LocalDate.now());
        aClass.setEndDate(LocalDate.now().plusDays(10));

        when(classesRepository.findByClassId(classId)).thenReturn(Optional.of(aClass));
        when(bookingRepository.findAllByClassIdAndBookingDate(classId, bookingDate)).thenReturn(new ArrayList<>());

        // Act & Assert
        bookingValidationService.validateBookingRequest(request);
    }

    @Test
    void validateParticipationDate_pastDate_throwsDateRangeException() {
        // Arrange
        LocalDate pastDate = LocalDate.now().minusDays(1);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Invalid participation date");

        // Act & Assert
        assertThrows(DateRangeException.class, () -> bookingValidationService.validateParticipationDate(pastDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }

    @Test
    void validateClassesCapacity_capacityExceeded_throwsCapacityException() {
        // Arrange
        UUID classId = UUID.randomUUID();
        LocalDate bookingDate = LocalDate.now().plusDays(1);
        Class aClass = new Class();
        aClass.setCapacity(2);

        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Booking booking3 = new Booking();

        when(classesRepository.findByClassId(classId)).thenReturn(Optional.of(aClass));
        when(bookingRepository.findAllByClassIdAndBookingDate(classId, bookingDate))
                .thenReturn(List.of(booking1, booking2, booking3));
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Capacity exceeded");

        // Act & Assert
        assertThrows(CapacityException.class, () -> bookingValidationService.validateClassesCapacity(classId, bookingDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }


    @Test
    void validateClassesAvailability_outsideDateRange_throwsClassesUnavailabilityException() {
        // Arrange
        Class aClass = new Class();
        aClass.setStartDate(LocalDate.now().plusDays(1));
        aClass.setEndDate(LocalDate.now().plusDays(10));
        LocalDate invalidDate = LocalDate.now();

        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Class not available for the given date");

        // Act & Assert
        assertThrows(ClassesUnavailabilityException.class, () -> bookingValidationService.validateClassesAvailability(aClass, invalidDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }

    @Test
    void validateClassesCapacity_classNotFound_throwsDataNotFoundException() {
        // Arrange
        UUID classId = UUID.randomUUID();
        LocalDate bookingDate = LocalDate.now().plusDays(1);

        when(classesRepository.findByClassId(classId)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Class not found");

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> bookingValidationService.validateClassesCapacity(classId, bookingDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }
}

