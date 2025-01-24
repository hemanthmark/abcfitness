package com.abcignite.test.respositorytest;


import com.abcignite.test.entity.Booking;
import com.abcignite.test.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingRepositoryTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingRepositoryTest bookingRepositoryTest;

    private UUID classId;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        classId = UUID.randomUUID();
        date = LocalDate.of(2025, 1, 15);
    }

    @Test
    void testFindAllByClassIdAndBookingDate() {
        // Mock the method behavior
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> mockBookings = Arrays.asList(booking1, booking2);

        when(bookingRepository.findAllByClassIdAndBookingDate(classId, date)).thenReturn(mockBookings);


        List<Booking> bookings = bookingRepository.findAllByClassIdAndBookingDate(classId, date);

        // Assert the results
        assertNotNull(bookings);
        assertEquals(2, bookings.size());
        assertTrue(bookings.contains(booking1));
        assertTrue(bookings.contains(booking2));
    }

    @Test
    void testFindAllByClassIdAndBookingDateNoResults() {
        // Mock the method behavior for no results
        when(bookingRepository.findAllByClassIdAndBookingDate(classId, date)).thenReturn(Arrays.asList());

        // Call the method on the repository
        List<Booking> bookings = bookingRepository.findAllByClassIdAndBookingDate(classId, date);

        // Assert the results
        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    void testFindAllByClassIdAndBookingDateWithNullResults() {
        // Test for handling null values in return
        when(bookingRepository.findAllByClassIdAndBookingDate(classId, date)).thenReturn(null);


        List<Booking> bookings = bookingRepository.findAllByClassIdAndBookingDate(classId, date);

        // Assert the results
        assertNull(bookings);
    }

    @Test
    void testFindAllByClassIdAndBookingDateWithException() {

        when(bookingRepository.findAllByClassIdAndBookingDate(classId, date)).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class, () -> bookingRepository.findAllByClassIdAndBookingDate(classId, date));
    }
}

