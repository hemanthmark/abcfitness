package com.abcignite.test.exceptions;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultExceptionHandler {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentExceptionHandler(MethodArgumentNotValidException exception){
        ProblemDetail errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
        Set<String> error = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet());
        errorDetail.setProperty("message", error);
        errorDetail.setProperty("type","invalid request");
        errorDetail.setProperty("code","400");
        Logger.error(error.toString());
        return errorDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail illegalArgumentException(IllegalArgumentException exception){
        ProblemDetail errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
        errorDetail.setProperty("message", List.of("Something went wrong"));
        Logger.error("Something went wrong {}",exception.getMessage());
        return errorDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail genericExceptionHandler(Exception ex){
        ProblemDetail errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
        errorDetail.setProperty("message",List.of("Some technical issue occurred"));
        Logger.error("Some technical issue occurred {}",ex.getMessage());
        return errorDetail;
    }


}
