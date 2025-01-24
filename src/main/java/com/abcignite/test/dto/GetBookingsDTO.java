package com.abcignite.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBookingsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6154360569114409910L;

    private String className;

    private String startTime;

    private String memberName;

    private LocalDate bookingDate;


}
