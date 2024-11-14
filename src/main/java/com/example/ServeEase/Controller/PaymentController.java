package com.example.ServeEase.Controller;

import com.example.ServeEase.DTO.PaymentDTO;
import com.example.ServeEase.Model.Payment;
import com.example.ServeEase.Repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepo paymentRepo;

    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody  PaymentDTO paymentDTO) {
        System.out.println(paymentDTO);
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setBookingId(paymentDTO.getBookingId());
        paymentRepo.save(payment);
         return ResponseEntity.ok("Payment added successfully");
    }
}
