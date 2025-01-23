package com.abcignite.test.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CapacityExceptionHandler {

    @ExceptionHandler(CapacityException.class)
    public ProblemDetail capacityExceededExceptionHandler(CapacityException exceededException){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
        problemDetail.setProperty("message", List.of(exceededException.getMessage().split(":")[1]));
        problemDetail.setProperty("code",exceededException.getMessage().split(":")[0]);
        return problemDetail;
    }


}
