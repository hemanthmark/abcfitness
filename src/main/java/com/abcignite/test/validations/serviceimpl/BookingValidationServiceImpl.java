package com.abcignite.test.validations.serviceimpl;

import com.abcignite.test.entity.Classes;
import com.abcignite.test.exceptions.CapacityException;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.validations.service.BookingValidationService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingValidationServiceImpl implements BookingValidationService {


    private final ClassesRepository classesRepository;

    private final BookingRepository bookingRepository;

    private final MessageSource messageSource;

    public BookingValidationServiceImpl(ClassesRepository classesRepository, BookingRepository bookingRepository, MessageSource messageSource) {
        this.classesRepository = classesRepository;
        this.bookingRepository = bookingRepository;
        this.messageSource = messageSource;
    }


    /**
     * @param request
     */
    @Override
    public void validateBookingRequest(CreateBookingRequest request) {

    }


    public void validateClassesCapacity(UUID classesId, LocalDate date){
        Classes classes = classesRepository.findById(classesId)
                .orElseThrow();
        int bookedSlots = bookingRepository.findAllByClassesIdAndParticipationDate(classesId,date).size();
        if(bookedSlots>=classes.getCapacity()){
            throw new CapacityException("Class capacity completed for the day");
        }
    }


    public void validateClassesAvailability(Classes classes, LocalDate participationDate){
        if(participationDate.isBefore(classes.getStartDate()) || participationDate.isAfter(classes.getEndDate())){

        }
    }

    public void validateParticipationDate(LocalDate participationDate){
        if(!participationDate.isAfter(LocalDate.now())){
            
        }
    }



}
