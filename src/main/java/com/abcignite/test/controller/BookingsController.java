package com.abcignite.test.controller;

import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.entity.Booking;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.response.bodies.GetSearchSuccessResponseBody;
import com.abcignite.test.response.bodies.SuccessResponseBody;
import com.abcignite.test.service.CreateBookingService;
import com.abcignite.test.service.SearchBookingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingsController {


    private final CreateBookingService createBookingService;

    private final SearchBookingsService searchBookingsService;

    public BookingsController(CreateBookingService createBookingService, SearchBookingsService searchBookingsService) {
        this.createBookingService = createBookingService;
        this.searchBookingsService = searchBookingsService;
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

    @Operation(summary = "fetch booking details based on search criteria")
    @GetMapping("/search")
    public ResponseEntity<GetSearchSuccessResponseBody> searchBookings(@RequestParam(required = false) String memberName,
                                                                       @RequestParam(required = false) LocalDate startDate,
                                                                       @RequestParam(required = false) LocalDate endDate,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size){
        Page<GetBookingsDTO> bookingsDTOS = searchBookingsService.searchBookings(memberName, startDate, endDate, page, size);
        GetSearchSuccessResponseBody searchResults = new GetSearchSuccessResponseBody("Bookings fetched Successfully",
                bookingsDTOS.map(booking -> (Object)booking));
        return new ResponseEntity<>(searchResults,HttpStatus.OK);

    }


}
