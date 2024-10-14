package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Review;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.provider.userId = :providerId")
    List<Review> findReviewsByProviderId(@Param("providerId") Long providerId);

    @Query("SELECT r FROM Review r WHERE r.seeker.userId = :seekerId")
    List<Review> findReviewsBySeekerId(@Param("seekerId") Long seekerId);
}
