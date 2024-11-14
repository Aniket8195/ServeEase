package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Booking;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findBySeeker(ServiceSeeker seeker);
    List<Booking> findByProvider(ServiceProvider provider);

    // Count total bookings by seeker
    int countBySeeker(ServiceSeeker seeker);

    // Count bookings by seeker and status
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.seeker = :seeker AND b.status = :status")
    int countBySeekerAndStatus(ServiceSeeker seeker, Booking.Status status);

    // Count total bookings by provider
    int countByProvider(ServiceProvider provider);

    // Count bookings by provider and status
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.provider = :provider AND b.status = :status")
    int countByProviderAndStatus(ServiceProvider provider, Booking.Status status);
}
