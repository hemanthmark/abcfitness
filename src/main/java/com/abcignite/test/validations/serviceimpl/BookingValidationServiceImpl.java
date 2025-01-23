package com.abcignite.test.validations.serviceimpl;

import com.abcignite.test.entity.Classes;
import com.abcignite.test.exceptions.*;
import com.abcignite.test.repository.BookingRepository;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateBookingRequest;
import com.abcignite.test.validations.service.BookingValidationService;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingValidationServiceImpl implements BookingValidationService {


    private final ClassesRepository classesRepository;

    private final BookingRepository bookingRepository;

    private final MessageSource messageSource;

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(BookingValidationServiceImpl.class);

    public BookingValidationServiceImpl(ClassesRepository classesRepository, BookingRepository bookingRepository, MessageSource messageSource) {
        this.classesRepository = classesRepository;
        this.bookingRepository = bookingRepository;
        this.messageSource = messageSource;
    }


    /**
     * @param request request
     */
    @Override
    public void validateBookingRequest(CreateBookingRequest request) {
        validateParticipationDate(request.getParticipationDate());
        validateClassesCapacity(request.getClassesId(),request.getParticipationDate());
    }


    public void validateClassesCapacity(UUID classesId, LocalDate date){
        Logger.info("validating classes for classId and particpation date {} {}",classesId,date);
        Classes classes = classesRepository.findByClassesId(classesId)
                .orElseThrow(() -> new DataNotFoundException(messageSource.getMessage(ResponseCodes.CLASSES_NOT_FOUND,null, LocaleContextHolder.getLocale())));
        int bookedSlots = bookingRepository.findAllByClassesIdAndParticipationDate(classesId,date).size();
        if(bookedSlots>=classes.getCapacity()){
            Logger.error("Slots not available for the class");
            throw new CapacityException(messageSource.getMessage(ResponseCodes.CAPACITY_EXCEEDED,null,LocaleContextHolder.getLocale()));
        }
        validateClassesAvailability(classes,date);
    }


    public void validateClassesAvailability(Classes classes, LocalDate participationDate){
        Logger.info("Validating classes availability for the given time period");
        if(participationDate.isBefore(classes.getStartDate()) || participationDate.isAfter(classes.getEndDate())){
            Logger.error("Given classes is not available for the given date");
            throw new ClassesUnavailabilityException(messageSource.getMessage(ResponseCodes.CLASSES_NOT_AVAILABLE,null,LocaleContextHolder.getLocale()));
        }
    }

    public void validateParticipationDate(LocalDate participationDate){
        Logger.info("Validating particpation date");
        if(!participationDate.isAfter(LocalDate.now())){
            Logger.error("invalid particpation date. Date must be future {}",participationDate);
            throw new DateRangeException(messageSource.getMessage(ResponseCodes.INVALID_PARTICIPATION_DATE,null,LocaleContextHolder.getLocale()));
        }
    }



}
