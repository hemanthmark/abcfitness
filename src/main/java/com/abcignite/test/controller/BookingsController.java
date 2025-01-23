package com.abcignite.test.controller;

import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.bodies.SuccessResponseBody;
import com.abcignite.test.service.CreateBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingsController {


    private final CreateBookingService createBookingService;

    public BookingsController(CreateBookingService createBookingService) {
        this.createBookingService = createBookingService;
    }

    @Operation(summary="creates bookings")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Bookings details created successfully"),
            @ApiResponse(responseCode="500", description="Something went wrong while creating booking"),
            @ApiResponse(responseCode="400",description="Bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseBody> createBooking(@Valid @RequestBody CreateBookingRequest createBookingRequest){
        SuccessResponseBody successResponseBody = new SuccessResponseBody("Bookings created Successfully",createBookingService.createBooking(createBookingRequest));
        return new ResponseEntity<>(successResponseBody, HttpStatus.CREATED);
    }


}
