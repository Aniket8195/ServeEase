package com.example.ServeEase.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSeeker extends User {
    @OneToMany(mappedBy = "seeker")
    @JsonBackReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "seeker")
    private List<Review> reviews;

    // Business Logic: Request Service, Book Service
}
