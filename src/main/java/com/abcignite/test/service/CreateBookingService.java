package com.abcignite.test.service;

import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.CreateBookingResponse;

public interface CreateBookingService {
    CreateBookingResponse createBooking(CreateBookingRequest request);
}
