package com.abcignite.test.validatorstest;



import com.abcignite.test.exceptions.CapacityException;
import com.abcignite.test.exceptions.DateRangeException;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.validations.serviceimpl.ValidateClassesCreationRequestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ValidateClassesCreationRequestImplTest {

    private ValidateClassesCreationRequestImpl validateClassesCreationRequest;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validateClassesCreationRequest = new ValidateClassesCreationRequestImpl(messageSource);
    }

    @Test
    void validateClassCapacity_belowThreshold_throwsCapacityException() {
        // Arrange
        int capacity = 0;
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Minimum capacity threshold not reached");

        // Act & Assert
        assertThrows(CapacityException.class, () -> validateClassesCreationRequest.validateClassCapacity(capacity));
        verify(messageSource).getMessage(anyString(), any(), any());
    }

    @Test
    void validateEndDate_invalidEndDate_throwsDateRangeException() {
        // Arrange
        LocalDate endDate = LocalDate.now().minusDays(1);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Invalid end date");

        // Act & Assert
        assertThrows(DateRangeException.class, () -> validateClassesCreationRequest.validateEndDate(endDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }

    @Test
    void validateDateRanges_invalidRange_throwsDateRangeException() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(10);
        LocalDate endDate = LocalDate.now().plusDays(5);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Invalid date range");

        // Act & Assert
        assertThrows(DateRangeException.class, () -> validateClassesCreationRequest.validateDateRanges(startDate, endDate));
        verify(messageSource).getMessage(anyString(), any(), any());
    }

    @Test
    void validateClassesCreationRequest_allValidationsPass_doesNotThrow() {
        // Arrange
        CreateClassesRequest request = new CreateClassesRequest();
        request.setCapacity(10);
        request.setStartDate(LocalDate.now().plusDays(1));
        request.setEndDate(LocalDate.now().plusDays(5));

        // Act & Assert
        validateClassesCreationRequest.validateClassesCreationRequest(request);
        verifyNoInteractions(messageSource); // Ensure no exceptions are thrown
    }
}

