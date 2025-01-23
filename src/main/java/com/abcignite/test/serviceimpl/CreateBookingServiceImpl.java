package com.abcignite.test.serviceimpl;

import com.abcignite.test.entity.Booking;
import com.abcignite.test.mapper.BookingsMapper;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.CreateBookingResponse;
import com.abcignite.test.service.CreateBookingService;
import com.abcignite.test.validations.service.BookingValidationService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateBookingServiceImpl implements CreateBookingService {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(CreateBookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final BookingsMapper bookingsMapper;

    private final BookingValidationService bookingValidationService;

    public CreateBookingServiceImpl(BookingRepository bookingRepository, BookingsMapper bookingsMapper, BookingValidationService bookingValidationService) {
        this.bookingRepository = bookingRepository;
        this.bookingsMapper = bookingsMapper;
        this.bookingValidationService = bookingValidationService;
    }

    /**
     * @param request
     * @return
     */
    @Transactional
    @Override
    public CreateBookingResponse createBooking(CreateBookingRequest request) {
        Logger.info("Create booking method started for request {}" ,request);
        bookingValidationService.validateBookingRequest(request);
        Booking booking = bookingsMapper.mapBookingRequestToBooking(request);
        setIdAndChangeLogTimeStamps(booking);
        CreateBookingResponse response = bookingsMapper.mapBookingToCreateBookingResponse(bookingRepository.save(booking));
        Logger.info("Create booking method completed with response {}" ,response);
        return response;
    }

    public void setIdAndChangeLogTimeStamps(Booking booking){
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setBookingId(UUID.randomUUID());
    }



}
