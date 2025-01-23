package com.abcignite.test.validations.serviceimpl;

import com.abcignite.test.exceptions.CapacityException;
import com.abcignite.test.exceptions.DateRangeException;
import com.abcignite.test.exceptions.ResponseCodes;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.validations.service.ClassesValidationService;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidateClassesCreationRequestImpl implements ClassesValidationService {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ValidateClassesCreationRequestImpl.class);

    private final MessageSource messageSource;

    public ValidateClassesCreationRequestImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public void validateClassesCreationRequest(CreateClassesRequest request){
        validateClassCapacity(request.getCapacity());
        validateEndDate(request.getEndDate());
        validateDateRanges(request.getStartDate(),request.getEndDate());
    }


    public void validateClassCapacity(int capacity){
        Logger.info("Validating classes capacity {}",capacity);
        if(capacity<1){
            Logger.error("Minimum capacity threshold not reached {}" ,"1");
            throw new CapacityException(messageSource.getMessage(ResponseCodes.MINIMUM_CAPACITY_THRESHOLD,null,
                    LocaleContextHolder.getLocale()));
        }
    }

    public void validateEndDate(LocalDate endDate){
        Logger.info("Validating classes end date {}",endDate);
        if(!endDate.isAfter(LocalDate.now())){
            Logger.error("Invalid end date");
            throw new DateRangeException(messageSource.getMessage(ResponseCodes.INVALID_END_DATE,null,
                    LocaleContextHolder.getLocale()));
        }
    }

    public void validateDateRanges(LocalDate startDate, LocalDate endDate){
        Logger.info("Validating data ranges startDate {} endDate {}",startDate,endDate);
        if(endDate.isBefore(startDate)){
            Logger.error("Invalid date range");
            throw new DateRangeException(messageSource.getMessage(ResponseCodes.INVALID_DATE_RANGE,null,
                    LocaleContextHolder.getLocale()));
        }
    }


}
