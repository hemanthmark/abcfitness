package com.abcignite.test.servicelayertest;



import com.abcignite.test.entity.Class;
import com.abcignite.test.mapper.ClassesMapper;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.abcignite.test.service.CreateClassesService;
import com.abcignite.test.serviceimpl.CreateClassesServiceImpl;
import com.abcignite.test.validations.service.ClassesValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateClassesServiceImplTest {

    private CreateClassesService createClassesService;

    @Mock
    private ClassesValidationService validateClassesCreationRequest;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private ClassesMapper classesMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createClassesService = new CreateClassesServiceImpl(validateClassesCreationRequest, classesRepository, classesMapper);
    }

    @Test
    void createClasses_validRequest_createsClassSuccessfully() {
        // Arrange
        CreateClassesRequest request = new CreateClassesRequest();
        request.setClassName("Gym 101");
        request.setCapacity(30);
        request.setStartDate(LocalDate.now().plusDays(1));
        request.setEndDate(LocalDate.now().plusDays(2));

        Class aClass = new Class();
        CreateClassesResponse response = new CreateClassesResponse();

        when(classesMapper.mapCreateClassesRequestToClasses(request)).thenReturn(aClass);
        when(classesRepository.save(any(Class.class))).thenReturn(aClass);
        when(classesMapper.mapClassesToCreateClassesResponse(aClass)).thenReturn(response);

        // Act
        CreateClassesResponse result = createClassesService.createClasses(request);

        // Assert
        assertNotNull(result);
        verify(validateClassesCreationRequest).validateClassesCreationRequest(request);
        verify(classesMapper).mapCreateClassesRequestToClasses(request);
        verify(classesRepository).save(any(Class.class));
        verify(classesMapper).mapClassesToCreateClassesResponse(aClass);
    }

    @Test
    void createClasses_validationFails_throwsException() {
        // Arrange
        CreateClassesRequest request = new CreateClassesRequest();
        request.setClassName("Gym 101");
        request.setCapacity(30);
        request.setStartDate(LocalDate.now().plusDays(1));
        request.setEndDate(LocalDate.now().plusDays(2));

        doThrow(new RuntimeException("Validation failed")).when(validateClassesCreationRequest).validateClassesCreationRequest(request);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> createClassesService.createClasses(request));
        verify(validateClassesCreationRequest).validateClassesCreationRequest(request);  // Ensure validation was called
    }


}

