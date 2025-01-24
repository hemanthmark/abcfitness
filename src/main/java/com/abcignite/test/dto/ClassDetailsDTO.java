package com.abcignite.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDetailsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2964125523589566854L;

    private String className;

    private String startTime;

}
