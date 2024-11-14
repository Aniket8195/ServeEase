package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.provider.userId = :providerId")
    List<Review> findReviewsByProviderId(@Param("providerId") Long providerId);

    @Query("SELECT r FROM Review r WHERE r.seeker.userId = :seekerId")
    List<Review> findReviewsBySeekerId(@Param("seekerId") Long seekerId);

    Optional<Review> findReviewByBookingId(Long bookingId);

    @Query("SELECT r FROM Review r WHERE r.bookingId = :bookingId AND r.seeker.userId = :seekerId")
    Optional<Review> findByBookingIdAndSeekerId(@Param("bookingId") Long bookingId, @Param("seekerId") Long seekerId);

    @Query("SELECT r FROM Review r WHERE r.bookingId = :bookingId AND r.provider.userId = :providerId")
    Optional<Review> findByBookingIdAndProviderId(@Param("bookingId") Long bookingId, @Param("providerId") Long providerId);


}
