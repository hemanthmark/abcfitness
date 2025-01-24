package com.abcignite.test.serviceimpl;

import com.abcignite.test.dto.ClassDetailsDTO;
import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.entity.Booking;
import com.abcignite.test.helper.BookingSpecificationHelper;
import com.abcignite.test.helper.GetClassDetailsHelper;
import com.abcignite.test.mapper.BookingDTOMapper;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.service.SearchBookingsService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
public class SearchBookingServiceImpl implements SearchBookingsService {


    private final BookingRepository bookingRepository;

    private final GetClassDetailsHelper classDetailsHelper;


    public SearchBookingServiceImpl(BookingRepository bookingRepository, GetClassDetailsHelper classDetailsHelper) {
        this.bookingRepository = bookingRepository;
        this.classDetailsHelper = classDetailsHelper;
    }

    /**
     * @param memberName represents member name for whoom bookings are searched for
     * @param startDate represents start date from which bookings has to be searched for
     * @param endDate represents end date until which bookings has to be returned
     * @return page list of GetBookingsDTO class
     */
    @Transactional(readOnly = true)
    @Override
    public Page<GetBookingsDTO> searchBookings(String memberName, LocalDate startDate, LocalDate endDate,int page,int size) {
        return getSearchResults(memberName, startDate, endDate, page, size);
    }

    public Page<GetBookingsDTO> getSearchResults(String memberName, LocalDate startDate, LocalDate endDate,int page,int size){
        Specification<Booking> spec = Specification
                .where(BookingSpecificationHelper.memberNameEquals(memberName))
                .and(BookingSpecificationHelper.bookingDateBetween(startDate, endDate));

        Page<Booking> bookings = bookingRepository.findAll(spec, createPageableRequest(page, size));
        Map<UUID, ClassDetailsDTO> classDetailsMap = classDetailsHelper.getClassDetails(bookings);

        return bookings.map(booking -> BookingDTOMapper.toDTO(booking, classDetailsMap.getOrDefault(booking.getClassId(), new ClassDetailsDTO("", ""))));

    }

    public Pageable createPageableRequest(int page, int size){
        return PageRequest.of(page, size, Sort.by(Sort.Order.desc("bookingDate")));
    }




}
