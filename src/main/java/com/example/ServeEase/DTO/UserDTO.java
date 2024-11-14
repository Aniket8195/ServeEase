package com.example.ServeEase.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String role;
    private float rating;
    private int totalBookings;
    private int completedBookings;
    private int activeBookings;

    // Add constructors to initialize the new fields

    public UserDTO(Long userId, String name, String email, String role, float rating, int totalBookings, int completedBookings, int activeBookings) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.rating = rating;
        this.totalBookings = totalBookings;
        this.completedBookings = completedBookings;
        this.activeBookings = activeBookings;
    }

    // Getters and Setters
}