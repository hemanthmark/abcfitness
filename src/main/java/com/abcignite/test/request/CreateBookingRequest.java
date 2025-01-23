package com.abcignite.test.request;

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
public class CreateBookingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8097860183804279955L;

    private UUID classId;

    private String memberName;

    private LocalDate participationDate;


}
