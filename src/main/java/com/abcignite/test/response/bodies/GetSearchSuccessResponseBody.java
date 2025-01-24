package com.abcignite.test.response.bodies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSearchSuccessResponseBody implements Serializable {

    @Serial
    private static final long serialVersionUID = 7860095731585105511L;

    private String message;

    private Page<Object> data;

}
