package com.abcignite.test.validations.service;

import com.abcignite.test.request.CreateBookingRequest;

public interface BookingValidationService {
    void validateBookingRequest(CreateBookingRequest request);
}
