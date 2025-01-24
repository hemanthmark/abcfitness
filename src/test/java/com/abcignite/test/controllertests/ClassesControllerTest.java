package com.abcignite.test.controllertests;


import com.abcignite.test.controller.ClassesController;
import com.abcignite.test.request.CreateClassesRequest;
import com.abcignite.test.response.CreateClassesResponse;
import com.abcignite.test.response.bodies.SuccessResponseBody;
import com.abcignite.test.service.CreateClassesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(ClassesController.class)
public class ClassesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateClassesService createClassesService;

    @InjectMocks
    private ClassesController classesController;

    private CreateClassesRequest createClassesRequest;
    private CreateClassesResponse createClassesResponse;

    @BeforeEach
    public void setup() {
        createClassesRequest = new CreateClassesRequest();
        createClassesRequest.setClassName("Math 101");
        createClassesRequest.setStartDate(LocalDate.of(2025, 1, 24));
        createClassesRequest.setEndDate(LocalDate.of(2025, 5, 24));
        createClassesRequest.setStartTime("10:00 AM");
        createClassesRequest.setDuration("2 hours");
        createClassesRequest.setCapacity(30);

        createClassesResponse = new CreateClassesResponse();
        createClassesResponse.setClassId(UUID.randomUUID());
        createClassesResponse.setClassName("Math 101");
        createClassesResponse.setStartDate(LocalDate.of(2025, 1, 24));
        createClassesResponse.setEndDate(LocalDate.of(2025, 5, 24));
        createClassesResponse.setStartTime("10:00 AM");
        createClassesResponse.setDuration("2 hours");
        createClassesResponse.setCapacity(30);
        createClassesResponse.setCreatedAt(LocalDateTime.now());
        createClassesResponse.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreateClasses() throws Exception {
        // Prepare mock data for the success response
        SuccessResponseBody successResponseBody = new SuccessResponseBody();
        successResponseBody.setMessage("Classes created successfully");
        successResponseBody.setData(createClassesResponse);

        // Mock the service call
        when(createClassesService.createClasses(createClassesRequest)).thenReturn(createClassesResponse);

        // Perform the test
        mockMvc.perform(post("/api/v1/classes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"className\":\"Math 101\",\"startDate\":\"2025-01-24\",\"endDate\":\"2025-05-24\",\"startTime\":\"10:00 AM\",\"duration\":\"2 hours\",\"capacity\":30}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"message\":\"Classes created successfully\",\"data\":{\"classId\":\"" + createClassesResponse.getClassId() + "\",\"className\":\"Math 101\",\"startDate\":\"2025-01-24\",\"endDate\":\"2025-05-24\",\"startTime\":\"10:00 AM\",\"duration\":\"2 hours\",\"capacity\":30}}"));
    }

    @Test
    public void testCreateClassesBadRequest() throws Exception {
        // Prepare invalid request data (missing required fields)
        String invalidRequestBody = "{\"className\":\"\",\"startDate\":\"\",\"endDate\":\"\",\"startTime\":\"\",\"duration\":\"\",\"capacity\":0}";

        // Perform the test for bad request
        mockMvc.perform(post("/api/v1/classes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

}

