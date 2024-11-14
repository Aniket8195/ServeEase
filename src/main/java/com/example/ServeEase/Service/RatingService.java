package com.example.ServeEase.Service;

import com.example.ServeEase.Model.Review;
import com.example.ServeEase.Model.User;
import com.example.ServeEase.Repository.ReviewRepository;
import com.example.ServeEase.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepo userRepo;

    public void calculateAverageProviderRating(Long providerId) {
        List<Review> reviews = reviewRepository.findReviewsByProviderId(providerId);


        List<Review> seekerReviews = reviews.stream()
                .filter(Review::isSeekerReview)
                .toList();


        float averageRating= (float) seekerReviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        Optional<User> provider = userRepo.findById(providerId);

        if (provider.isPresent()) {
            User user = provider.get();
            user.setRating(averageRating);
            userRepo.save(user);
        }

    }

    public void calculateAverageSeekerRating(Long seekerId) {
        List<Review> reviews = reviewRepository.findReviewsBySeekerId(seekerId);


        List<Review> providerReviews = reviews.stream()
                .filter(review -> !review.isSeekerReview())
                .toList();


        float averageRating = (float) providerReviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);


        Optional<User> seeker = userRepo.findById(seekerId);

        if (seeker.isPresent()) {
            User user = seeker.get();
            user.setRating(averageRating);
            userRepo.save(user);
        }
    }

    public float getRatingByBookingId(Long bookingId) {
        Optional<Review> review = reviewRepository.findReviewByBookingId(bookingId);
        return review.map(Review::getRating).orElse(0.0f);
    }
}

