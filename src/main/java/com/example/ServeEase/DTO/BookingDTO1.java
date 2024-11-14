package com.example.ServeEase.DTO;

import com.example.ServeEase.Model.Booking;

import java.time.LocalDateTime;

public class BookingDTO1 {
    private Long bookingId;
    private String categoryName; // Category name instead of full object
    private Booking.Status status;
    private float rating;
    private LocalDateTime bookingDate;
    private int providerId;
    private int seekerId;
    private boolean paid;

    // Constructors
    public BookingDTO1(Long bookingId, String categoryName, Booking.Status status, LocalDateTime bookingDate, float rating, int providerId, int seekerId, boolean paid) {
        this.bookingId = bookingId;
        this.categoryName = categoryName;
        this.status = status;
        this.rating = rating;
        this.bookingDate = bookingDate;
        this.providerId = providerId;
        this.seekerId = seekerId;
        this.paid = paid;

    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Booking.Status getStatus() {
        return status;
    }

    public void setStatus(Booking.Status status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(int seekerId) {
        this.seekerId = seekerId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
