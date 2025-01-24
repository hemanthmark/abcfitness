package com.abcignite.test.mapper;

import com.abcignite.test.dto.ClassDetailsDTO;
import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.entity.Booking;

public class BookingDTOMapper {


        public static GetBookingsDTO toDTO(Booking booking, ClassDetailsDTO details) {
            return new GetBookingsDTO(
                    details.getClassName(),
                    details.getStartTime(),
                    booking.getMemberName(),
                    booking.getBookingDate()
            );
        }


}
