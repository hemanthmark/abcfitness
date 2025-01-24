package com.abcignite.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking implements Serializable {

    @Serial
    private static final long serialVersionUID = 6859896865129640828L;

    @Id
    @Column(name = "booking_id")
    private UUID bookingId;

    @Column(name = "class_id",nullable = false)
    private UUID classId;

    @Column(name = "member_name",nullable = false)
    private String memberName;

    @Column(name = "booking_date",nullable = false)
    private LocalDate bookingDate;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;


}
