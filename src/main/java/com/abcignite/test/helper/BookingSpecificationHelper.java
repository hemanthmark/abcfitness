package com.abcignite.test.helper;

import com.abcignite.test.entity.Booking;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookingSpecificationHelper {

    public static Specification<Booking> memberNameEquals(String memberName) {
        return (root, query, criteriaBuilder) -> memberName == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("memberName"), memberName);
    }

    public static Specification<Booking> bookingDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bookingDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bookingDate"), endDate));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



}
