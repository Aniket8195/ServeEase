package com.example.ServeEase.DTO;

import com.example.ServeEase.Model.Booking;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long bookingId;
    private String categoryName; // Category name instead of full object
    private Booking.Status status;
    private LocalDateTime bookingDate;

    // Constructors
    public BookingDTO(Long bookingId, String categoryName, Booking.Status status, LocalDateTime bookingDate) {
        this.bookingId = bookingId;
        this.categoryName = categoryName;
        this.status = status;
        this.bookingDate = bookingDate;

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
}

