package com.abcignite.test.serviceimpl;

import com.abcignite.test.entity.Classes;
import com.abcignite.test.mapper.ClassesMapper;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.abcignite.test.service.CreateClassesService;
import com.abcignite.test.validations.service.ClassesValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateClassesServiceImpl implements CreateClassesService {

    private final ClassesValidationService validateClassesCreationRequest;

    private final ClassesRepository classesRepository;

    private final ClassesMapper classesMapper;

    public CreateClassesServiceImpl(ClassesValidationService validateClassesCreationRequest, ClassesRepository classesRepository, ClassesMapper classesMapper) {
        this.validateClassesCreationRequest = validateClassesCreationRequest;
        this.classesRepository = classesRepository;
        this.classesMapper = classesMapper;
    }

    /**
     * @param request represents the data rrequired for creating the classes
     * @return response with persisted data
     */
    @Transactional
    @Override
    public CreateClassesResponse createClasses(CreateClassesRequest request) {
        validateClassesCreationRequest.validateClassesCreationRequest(request);
        Classes classes = classesMapper.mapCreateClassesRequestToClasses(request);
        updateClassesWithIdAndTimeStamps(classes);
        return classesMapper.mapClassesToCreateClassesResponse(classesRepository.save(classes));
    }

    private void updateClassesWithIdAndTimeStamps(Classes classes){
        classes.setClassesId(UUID.randomUUID());
        classes.setCreatedAt(LocalDateTime.now());
        classes.setUpdatedAt(LocalDateTime.now());
    }



}
