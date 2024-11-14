package com.example.ServeEase.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "seeker_id")
    private ServiceSeeker seeker;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;

//    @ManyToOne
//    @JoinColumn(name = "service_id")
//    private Service service;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    @Column(name = "booking_id")
    private Long bookingId;

    private float rating;
    private String comments;

    private boolean isSeekerReview;

}

