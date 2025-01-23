package com.abcignite.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class Classes implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1047750184780262437L;

    @Id
	private UUID classesId;

    @Column(name = "classes_name",nullable = false)
    private String classesName;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time",nullable = false)
    private String startTime;

    @Column(name = "duration",nullable=false)
    private String duration;

    @Min(value = 1,message = "Minimum value should be at least 1")
    @Column(name = "capacity")
    private int capacity;

    @Column(name = "created_at",nullable=false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "classes_id",referencedColumnName = "classes_id")
    private List<Booking> bookings;

}
