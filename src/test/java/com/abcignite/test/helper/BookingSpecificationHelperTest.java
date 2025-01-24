package com.abcignite.test.helper;


import com.abcignite.test.entity.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDate;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookingSpecificationHelperTest {

    private BookingSpecificationHelper bookingSpecificationHelper;



    @Test
    void testMemberNameEquals() {
        // Test when memberName is null
        Specification<Booking> spec = BookingSpecificationHelper.memberNameEquals(null);
        assertNotNull(spec);

        // Test when memberName is not null
        spec = BookingSpecificationHelper.memberNameEquals("John");
        assertNotNull(spec);

        // You could also test the specification behavior with mocking, but for now, we are checking if the specification is not null
    }

    @Test
    void testBookingDateBetween() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);

        // Test when both startDate and endDate are not null
        Specification<Booking> spec = BookingSpecificationHelper.bookingDateBetween(startDate, endDate);
        assertNotNull(spec);

        // Test when startDate is null
        spec = BookingSpecificationHelper.bookingDateBetween(null, endDate);
        assertNotNull(spec);

        // Test when endDate is null
        spec = BookingSpecificationHelper.bookingDateBetween(startDate, null);
        assertNotNull(spec);

        // Test when both startDate and endDate are null
        spec = BookingSpecificationHelper.bookingDateBetween(null, null);
        assertNotNull(spec);
    }
}

