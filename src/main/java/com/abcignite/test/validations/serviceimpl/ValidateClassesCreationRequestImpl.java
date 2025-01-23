package com.abcignite.test.validations.serviceimpl;

import com.abcignite.test.exceptions.CapacityException;
import com.abcignite.test.exceptions.ResponseCodes;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.validations.service.ClassesValidationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidateClassesCreationRequestImpl implements ClassesValidationService {

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
        if(capacity<1){
            throw new CapacityException(messageSource.getMessage(ResponseCodes.MINIMUM_CAPACITY_THRESHOLD,null,
                    LocaleContextHolder.getLocale()));
        }
    }

    public void validateEndDate(LocalDate endDate){
        if(!endDate.isAfter(LocalDate.now())){
            throw new CapacityException(messageSource.getMessage(ResponseCodes.INVALID_END_DATE,null,
                    LocaleContextHolder.getLocale()));
        }
    }

    public void validateDateRanges(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)){
            throw new RuntimeException(messageSource.getMessage(ResponseCodes.INVALID_DATE_RANGE,null,
                    LocaleContextHolder.getLocale()));
        }
    }


}
