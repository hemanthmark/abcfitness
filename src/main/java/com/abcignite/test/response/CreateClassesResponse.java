package com.abcignite.test.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassesResponse implements Serializable {

    private UUID classesId;

    private String classesName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String startTime;

    private String duration;

    private int capacity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
