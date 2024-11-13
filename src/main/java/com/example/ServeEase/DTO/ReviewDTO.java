package com.example.ServeEase.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewDTO {
    private Long seekerId; // The seeker providing the review
    private Long providerId; // The provider receiving the review
    private float rating;
    private String comments;
    private Long bookingId;
    private boolean isSeekerReview;
}