package com.abcignite.test.validations.service;

import com.abcignite.test.request.CreateClassesRequest;

public interface ClassesValidationService {

    void validateClassesCreationRequest(CreateClassesRequest request);

}
