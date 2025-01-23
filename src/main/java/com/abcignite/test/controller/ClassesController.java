package com.abcignite.test.controller;

import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.abcignite.test.response.bodies.SuccessResponseBody;
import com.abcignite.test.service.CreateClassesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassesController {


    private final CreateClassesService createClassesService;

    public ClassesController(CreateClassesService createClassesService) {
        this.createClassesService = createClassesService;
    }

    @Operation(summary="creates classes")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Classes details created successfully"),
            @ApiResponse(responseCode="500", description="Something went wrong while creating classes"),
            @ApiResponse(responseCode="400",description="Bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<SuccessResponseBody> createClasses(@Valid @RequestBody CreateClassesRequest request){
        SuccessResponseBody responseBody = new SuccessResponseBody();
        CreateClassesResponse response = createClassesService.createClasses(request);
        responseBody.setData(response);
        responseBody.setMessage("Classes created successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }





}
