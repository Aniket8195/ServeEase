package com.example.ServeEase.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private Long providerId;
    private Long seekerId;
    private Long categoryId;
    private LocalDateTime bookingDate;
}