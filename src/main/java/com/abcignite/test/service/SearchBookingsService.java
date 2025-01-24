package com.abcignite.test.service;

import com.abcignite.test.dto.GetBookingsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SearchBookingsService {
    Page<GetBookingsDTO> searchBookings(String memberName, LocalDate startDate, LocalDate endDate,int page,int size);
}
