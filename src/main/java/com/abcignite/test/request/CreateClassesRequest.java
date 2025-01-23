package com.abcignite.test.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassesRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5360656238462358219L;

    @Size(min = 3,message = "Name has to be at least 3 letters")
    @NotNull(message = "name cannot be null")
    private String classesName;

    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "end date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "start time cannot be null")
    private String startTime;

    @NotNull(message = "duration cannot be null")
    private String duration;

    @Min(value = 1,message = "Capacity should be at least 1")
    private int capacity;

}
