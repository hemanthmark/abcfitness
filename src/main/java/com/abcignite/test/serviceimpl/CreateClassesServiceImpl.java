package com.abcignite.test.serviceimpl;

import com.abcignite.test.entity.Classes;
import com.abcignite.test.mapper.ClassesMapper;
import com.abcignite.test.repository.ClassesRepository;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.abcignite.test.service.CreateClassesService;
import com.abcignite.test.validations.service.ClassesValidationService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateClassesServiceImpl implements CreateClassesService {


    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(CreateClassesServiceImpl.class);

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
        Logger.info("Create classes method started for request {}",request);
        validateClassesCreationRequest.validateClassesCreationRequest(request);
        Classes classes = classesMapper.mapCreateClassesRequestToClasses(request);
        updateClassesWithIdAndTimeStamps(classes);
        CreateClassesResponse response = classesMapper.mapClassesToCreateClassesResponse(classesRepository.save(classes));
        Logger.info("Create classes method completed and response {}",response);
        return response;
    }

    private void updateClassesWithIdAndTimeStamps(Classes classes){
        classes.setClassesId(UUID.randomUUID());
        classes.setCreatedAt(LocalDateTime.now());
        classes.setUpdatedAt(LocalDateTime.now());
    }



}
