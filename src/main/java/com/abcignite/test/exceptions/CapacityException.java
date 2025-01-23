package com.abcignite.test.exceptions;

import java.io.Serial;

public class CapacityException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 6588106443303100239L;

    public CapacityException(String message){
        super(message);
    }

}
