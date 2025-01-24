package com.abcignite.test.servicelayertest;



import com.abcignite.test.dto.ClassDetailsDTO;
import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.entity.Booking;
import com.abcignite.test.helper.GetClassDetailsHelper;
import com.abcignite.test.mapper.BookingDTOMapper;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.serviceimpl.SearchBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SearchBookingServiceImplTest {

    private SearchBookingServiceImpl searchBookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GetClassDetailsHelper classDetailsHelper;

    @Mock
    private BookingDTOMapper bookingDTOMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchBookingService = new SearchBookingServiceImpl(bookingRepository, classDetailsHelper);
    }

    @Test
    void searchBookings_validRequest_returnsPageOfBookings() {
        // Arrange
        String memberName = "John Doe";
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now().plusDays(5);
        int page = 0;
        int size = 10;

        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID());
        booking.setClassId(UUID.randomUUID());
        booking.setMemberName(memberName);
        booking.setBookingDate(LocalDate.now());

        Page<Booking> bookingsPage = new PageImpl<>(List.of(booking), PageRequest.of(page, size), 1);

        ClassDetailsDTO classDetailsDTO = new ClassDetailsDTO("Math 101", "Room 102");
        Map<UUID, ClassDetailsDTO> classDetailsMap = Map.of(booking.getClassId(), classDetailsDTO);

        // Cast the argument to Specification<Booking>
        when(bookingRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(bookingsPage);
        when(classDetailsHelper.getClassDetails(bookingsPage)).thenReturn(classDetailsMap);


        // Act
        Page<GetBookingsDTO> result = searchBookingService.searchBookings(memberName, startDate, endDate, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(bookingRepository).findAll(any(Specification.class), any(Pageable.class)); // Ensure the correct method is called
        verify(classDetailsHelper).getClassDetails(bookingsPage);
    }


    @Test
    void getSearchResults_validRequest_returnsMappedPage() {
        // Arrange
        String memberName = "John Doe";
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now().plusDays(5);
        int page = 0;
        int size = 10;

        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID());
        booking.setClassId(UUID.randomUUID());
        booking.setMemberName(memberName);
        booking.setBookingDate(LocalDate.now());

        Page<Booking> bookingsPage = new PageImpl<>(List.of(booking), PageRequest.of(page, size), 1);

        ClassDetailsDTO classDetailsDTO = new ClassDetailsDTO("Gym 101", "Room 102");
        Map<UUID, ClassDetailsDTO> classDetailsMap = Map.of(booking.getClassId(), classDetailsDTO);

        when(bookingRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(bookingsPage);
        when(classDetailsHelper.getClassDetails(bookingsPage)).thenReturn(classDetailsMap);


        // Act
        Page<GetBookingsDTO> result = searchBookingService.getSearchResults(memberName, startDate, endDate, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(bookingRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(classDetailsHelper).getClassDetails(bookingsPage);
    }

    @Test
    void createPageableRequest_validParams_returnsPageable() {
        // Arrange
        int page = 0;
        int size = 10;

        // Act
        Pageable result = searchBookingService.createPageableRequest(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
    }
}

