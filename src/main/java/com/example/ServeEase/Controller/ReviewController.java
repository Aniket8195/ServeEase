package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.ReviewDTO;
import com.example.ServeEase.Model.Category;
import com.example.ServeEase.Model.Review;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Repository.*;
import com.example.ServeEase.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ServiceSeekerRepo serviceSeekerRepo;

    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private RatingService ratingService;




    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO) {
        try {

            Optional<ServiceSeeker> seekerOpt = serviceSeekerRepo.findById(reviewDTO.getSeekerId());
            Optional<ServiceProvider> providerOpt = serviceProviderRepo.findById(reviewDTO.getProviderId());
//            Optional<Category> categoryOpt = categoryRepo.findById(reviewDTO.getCategoryId());

            if (seekerOpt.isEmpty() || providerOpt.isEmpty() ) {
                return ResponseEntity.badRequest().body("Invalid seeker, provider, or category ID.");
            }

            Review review = new Review();
            review.setSeeker(seekerOpt.get());
            review.setProvider(providerOpt.get());
            //review.setCategory(categoryOpt.get());
            review.setRating(reviewDTO.getRating());
            review.setComments(reviewDTO.getComments());

            review.setSeekerReview(reviewDTO.isSeekerReview());


            ratingService.calculateAverageProviderRating(reviewDTO.getProviderId());
            ratingService.calculateAverageSeekerRating(reviewDTO.getSeekerId());

            reviewRepo.save(review);
            return ResponseEntity.ok("Review added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding review: " + e.getMessage());
        }
    }


    @GetMapping("/provider/{providerId}")
    public ResponseEntity<?> getReviewsForProvider(@PathVariable Long providerId) {
        try {
            List<Review> reviews = reviewRepo.findReviewsByProviderId(providerId);
            if (reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No reviews found for this provider.");
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving reviews: " + e.getMessage());
        }
    }


    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<?> getReviewsForSeeker(@PathVariable Long seekerId) {
        try {
            List<Review> reviews = reviewRepo.findReviewsBySeekerId(seekerId);
            if (reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No reviews found for this seeker.");
            }
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving reviews: " + e.getMessage());
        }
    }

}
