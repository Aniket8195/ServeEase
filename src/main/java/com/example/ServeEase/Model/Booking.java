package com.example.ServeEase.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "seeker_id")
    @JsonManagedReference
    private ServiceSeeker seeker;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonManagedReference
    private ServiceProvider provider;

//    @ManyToOne
//    @JoinColumn(name = "service_id")
//    private Service service;

    @ManyToOne
    @JoinColumn(name = "category_id") // If you want to link to Category
    private Category category;


    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime bookingDate;

    public enum Status {
        PENDING, CONFIRMED, CANCELED, COMPLETED
    }

    // Getters and Setters
}
