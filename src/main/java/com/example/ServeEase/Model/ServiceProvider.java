package com.example.ServeEase.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProvider extends User {
//    @OneToMany(mappedBy = "serviceProvider")
//    private List<Service> services;

    @ManyToMany
    @JoinTable(
            name = "provider_category",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "provider")
    @JsonBackReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "provider")
    private List<Review> reviews;

    // Business Logic: Manage Bookings, Respond to Requests
}
