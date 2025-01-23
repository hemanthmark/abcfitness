package com.abcignite.test.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class DateRangeExceptionHandler {

    @ExceptionHandler(DateRangeException.class)
    public ProblemDetail invalidDateRangeException(DateRangeException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
        problemDetail.setProperty("message", List.of(exception.getMessage().split(":")[1]));
        problemDetail.setProperty("code",exception.getMessage().split(":")[0]);
        return problemDetail;
    }


}
