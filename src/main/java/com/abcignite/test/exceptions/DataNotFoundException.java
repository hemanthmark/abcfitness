package com.abcignite.test.exceptions;

import java.io.Serial;

public class DataNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3617753443189710852L;

    public DataNotFoundException(String message){
        super(message);
    }

}
