package com.abcignite.test.mapper;

import com.abcignite.test.entity.Class;
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


    public Class mapCreateClassesRequestToClasses(CreateClassesRequest request){
        return mapper.convertValue(request, Class.class);
    }

    public CreateClassesResponse mapClassesToCreateClassesResponse(Class aClass){
        return mapper.convertValue(aClass,CreateClassesResponse.class);
    }


}
