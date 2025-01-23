package com.abcignite.test.mapper;

import com.abcignite.test.entity.Booking;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.CreateBookingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingsMapper {


    private final ObjectMapper mapper;

    public BookingsMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Booking mapBookingRequestToBooking(CreateBookingRequest request){
        return mapper.convertValue(request, Booking.class);
    }


    public CreateBookingResponse mapBookingToCreateBookingResponse(Booking booking){
        return mapper.convertValue(booking, CreateBookingResponse.class);
    }

}
