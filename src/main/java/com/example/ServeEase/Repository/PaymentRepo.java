package com.example.ServeEase.Repository;

import com.example.ServeEase.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
    Optional<Payment> findByBookingId(Long bookingId);
}
