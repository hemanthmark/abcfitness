package com.abcignite.test.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ClassesUnavialabilityExceptionHandler {

    @ExceptionHandler(ClassesUnavailabilityException.class)
    public ProblemDetail classesUnavailabilityExceptionHandler(ClassesUnavailabilityException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(404));
        problemDetail.setProperty("message", List.of(exception.getMessage().split(":")[1]));
        problemDetail.setProperty("code",exception.getMessage().split(":")[0]);
        return problemDetail;
    }

}
