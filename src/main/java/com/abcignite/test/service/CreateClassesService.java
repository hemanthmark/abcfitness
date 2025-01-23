package com.abcignite.test.service;

import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;

public interface CreateClassesService {
    CreateClassesResponse createClasses(CreateClassesRequest request);
}
