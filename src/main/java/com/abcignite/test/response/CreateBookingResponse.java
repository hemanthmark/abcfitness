package com.abcignite.test.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1618792483356583055L;

    private UUID bookingId;

    private UUID classesId;

    private String memberName;

    private LocalDate participationDate;


}
