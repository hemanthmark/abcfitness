package com.abcignite.test.mapper;

import com.abcignite.test.entity.Classes;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ClassesMapper {


    private final ObjectMapper mapper;

    public ClassesMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public Classes mapCreateClassesRequestToClasses(CreateClassesRequest request){
        return mapper.convertValue(request,Classes.class);
    }

    public CreateClassesResponse mapClassesToCreateClassesResponse(Classes classes){
        return mapper.convertValue(classes,CreateClassesResponse.class);
    }


}
