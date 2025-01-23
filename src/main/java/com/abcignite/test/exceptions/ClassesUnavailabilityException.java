package com.abcignite.test.exceptions;

import java.io.Serial;

public class ClassesUnavailabilityException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -3758012348450895660L;

    public ClassesUnavailabilityException(String message){
        super(message);
    }

}
