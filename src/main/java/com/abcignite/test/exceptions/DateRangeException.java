package com.abcignite.test.exceptions;

import java.io.Serial;

public class DateRangeException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -6992199713621039428L;

    public DateRangeException(String message){
        super(message);
    }

}
