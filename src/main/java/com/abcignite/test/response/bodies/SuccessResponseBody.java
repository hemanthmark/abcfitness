package com.abcignite.test.response.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseBody implements Serializable {

    private String message;

    private Object data;

}
