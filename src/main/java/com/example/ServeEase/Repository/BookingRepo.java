package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Booking;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findBySeeker(ServiceSeeker seeker);
    List<Booking> findByProvider(ServiceProvider provider);
}
