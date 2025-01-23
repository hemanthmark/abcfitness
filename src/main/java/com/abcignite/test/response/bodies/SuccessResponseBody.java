package com.abcignite.test.response.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -2111848362052166511L;
    
    private String message;

    private Object data;

}
