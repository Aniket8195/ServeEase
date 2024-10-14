package com.example.ServeEase.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private float amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public abstract void processPayment();
    public abstract void refundPayment();
}

