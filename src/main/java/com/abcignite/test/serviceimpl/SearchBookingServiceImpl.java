package com.abcignite.test.serviceimpl;

import com.abcignite.test.dto.GetBookingsDTO;
import com.abcignite.test.entity.Booking;
import com.abcignite.test.entity.Class;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.service.SearchBookingsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchBookingServiceImpl implements SearchBookingsService {


    private final BookingRepository bookingRepository;

    private final ClassesRepository classesRepository;

    public SearchBookingServiceImpl(BookingRepository bookingRepository, ClassesRepository classesRepository) {
        this.bookingRepository = bookingRepository;
        this.classesRepository = classesRepository;
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
        Specification<Booking> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (memberName != null) {
                predicates.add(criteriaBuilder.equal(root.get("memberName"), memberName));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bookingDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bookingDate"), endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Booking> bookings =  bookingRepository.findAll(spec,createPageableRequest(page, size));
        Map<UUID,String> classIdAndNameMap = getClassIdAndNameMap(bookings);
        Map<UUID,String> classIdAndStratTimeMap = getClassIdAndStartTimMap(bookings);
        return bookings.map(booking -> new GetBookingsDTO(
                classIdAndNameMap.getOrDefault(booking.getClassId(), ""),
                classIdAndStratTimeMap.getOrDefault(booking.getClassId(), ""),
                booking.getMemberName(),
                booking.getBookingDate()
        ));
    }

    public Pageable createPageableRequest(int page, int size){
        return PageRequest.of(page, size, Sort.by(Sort.Order.desc("bookingDate")));
    }

    public Map<UUID,String> getClassIdAndNameMap(Page<Booking> bookings){
        List<UUID> classIds = bookings.stream().map(Booking::getClassId).toList();
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);
       return classes.stream().collect(Collectors.toMap(Class::getClassId,Class::getClassName));
    }

    public Map<UUID,String> getClassIdAndStartTimMap(Page<Booking> bookings){
        List<UUID> classIds = bookings.stream().map(Booking::getClassId).toList();
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);
        return classes.stream().collect(Collectors.toMap(Class::getClassId,Class::getStartTime));
    }




}
