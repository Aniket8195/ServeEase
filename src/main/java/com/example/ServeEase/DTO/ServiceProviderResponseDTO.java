package com.example.ServeEase.DTO;

// ServiceProviderDTO.java
public class ServiceProviderResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private float  rating;

    // Constructor
    public ServiceProviderResponseDTO(Long userId, String name, String email, float rating) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.rating = rating;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
