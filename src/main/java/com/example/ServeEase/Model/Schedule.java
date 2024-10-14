package com.example.ServeEase.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private LocalDateTime availableSlots;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    // Additional scheduling logic (time zones, conflicts)
}

