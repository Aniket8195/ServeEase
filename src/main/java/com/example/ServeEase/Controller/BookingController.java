package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.BookingDTO;
import com.example.ServeEase.DTO.BookingDTO1;
import com.example.ServeEase.DTO.BookingRequestDTO;
import com.example.ServeEase.Model.Booking;
import com.example.ServeEase.Model.Category;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Repository.BookingRepo;
import com.example.ServeEase.Repository.CategoryRepo;
import com.example.ServeEase.Repository.ServiceProviderRepo;
import com.example.ServeEase.Repository.ServiceSeekerRepo;
import com.example.ServeEase.Service.EmailService;
import com.example.ServeEase.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ServiceSeekerRepo serviceSeekerRepo;

    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private EmailService emailService;


    @Autowired
    private RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody BookingRequestDTO bookingRequest) {
        try {
            Optional<ServiceProvider> providerOpt = serviceProviderRepo.findById(bookingRequest.getProviderId());
            Optional<ServiceSeeker> seekerOpt = serviceSeekerRepo.findById(bookingRequest.getSeekerId());
            Optional<Category> categoryOpt = categoryRepo.findById(bookingRequest.getCategoryId());
            System.out.println("provider ID: " + bookingRequest.getProviderId());

            if (providerOpt.isEmpty() || seekerOpt.isEmpty() || categoryOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Provider, Seeker, or Category not found.");
            }
            if (bookingRequest.getBookingDate().isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Booking date must be in the future.");
            }

            Booking booking = new Booking();
            booking.setProvider(providerOpt.get());
            booking.setSeeker(seekerOpt.get());
            booking.setCategory(categoryOpt.get());
            booking.setStatus(Booking.Status.PENDING);
            booking.setBookingDate(bookingRequest.getBookingDate());
            bookingRepo.save(booking);

            // Send email to provider
//            emailService.sendBookingEmail(providerOpt.get().getEmail(), "New Booking Request",
//                    "You have a new booking request for " + categoryOpt.get().getCategoryName() + " on " + bookingRequest.getBookingDate() + ". Please login to ServeEase to confirm or cancel the booking.");
//
//            // Send email to seeker
//            emailService.sendBookingEmail(seekerOpt.get().getEmail(), "Booking Request Sent",
//                    "Your booking request for " + categoryOpt.get().getCategoryName() + " on " + bookingRequest.getBookingDate() + " has been sent to the provider. You will receive an email once the provider confirms or cancels the booking.");

            return ResponseEntity.ok("Booking created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating booking: " + e.getMessage());
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {

        Optional<Booking> bookingOpt = bookingRepo.findById(id);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }
        Booking booking = bookingOpt.get();
//        emailService.sendBookingEmail(booking.getProvider().getEmail(), "Booking Canceled",
//                "The booking with " + booking.getSeeker().getEmail()+ " " + booking.getSeeker().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been canceled.");
//
//        emailService.sendBookingEmail(booking.getSeeker().getEmail(), "Booking Canceled",
//                "The booking with " + booking.getProvider().getEmail() + " " + booking.getProvider().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been canceled.");


        return updateBookingStatus(id, Booking.Status.CANCELED);
    }

    // Method to confirm a booking
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id) {

        Optional<Booking> bookingOpt = bookingRepo.findById(id);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }
        Booking booking = bookingOpt.get();
//        emailService.sendBookingEmail(booking.getProvider().getEmail(), "Booking Confirmed",
//                "The booking with " + booking.getSeeker().getEmail() + " " + booking.getSeeker().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been confirmed.");
//
//        emailService.sendBookingEmail(booking.getSeeker().getEmail(), "Booking Confirmed",
//                "The booking with " + booking.getProvider().getEmail() + " " + booking.getProvider().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been confirmed.");

        return updateBookingStatus(id, Booking.Status.CONFIRMED);
    }

    // Method to complete a booking
    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeBooking(@PathVariable Long id) {

        Optional<Booking> bookingOpt = bookingRepo.findById(id);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }
        Booking booking = bookingOpt.get();
//        emailService.sendBookingEmail(booking.getProvider().getEmail(), "Booking Completed",
//                "The booking with " + booking.getSeeker().getEmail() + " " + booking.getSeeker().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been completed.");
//
//        emailService.sendBookingEmail(booking.getSeeker().getEmail(), "Booking Completed",
//                "The booking with " + booking.getProvider().getEmail() + " " + booking.getProvider().getName() + " for " + booking.getCategory().getCategoryName() + " on " + booking.getBookingDate() + " has been completed.");
//

        return updateBookingStatus(id, Booking.Status.COMPLETED);
    }
    private ResponseEntity<?> updateBookingStatus(Long id, Booking.Status status) {
        try {
            Optional<Booking> bookingOpt = bookingRepo.findById(id);
            if (bookingOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Booking not found.");
            }

            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            bookingRepo.save(booking);

            return ResponseEntity.ok("Booking status updated successfully.");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while updating booking status: " + e.getMessage());
        }
    }

    // Method to get all bookings for a specific service seeker
    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<?> getBookingsBySeeker(@PathVariable Long seekerId) {
        try {
            Optional<ServiceSeeker> seekerOpt = serviceSeekerRepo.findById(seekerId);
            if (seekerOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service seeker not found.");
            }

            List<Booking> bookings = bookingRepo.findBySeeker(seekerOpt.get());
            if (bookings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No bookings found for this seeker.");
            }

            // Map Booking to BookingDTO
            List<BookingDTO> bookingDTOs = bookings.stream()
                    .map(booking -> new BookingDTO(
                            booking.getBookingId(),
                            booking.getCategory() != null ? booking.getCategory().getCategoryName() : null,
                            booking.getStatus(),
                            booking.getBookingDate()
                    ))
                    .toList();

            List<BookingDTO1> bookingDTOs1 = bookings.stream()
                    .map(booking -> new BookingDTO1(
                            booking.getBookingId(),
                            booking.getCategory() != null ? booking.getCategory().getCategoryName() : null,
                            booking.getStatus(),
                            booking.getBookingDate(),
                            ratingService.getRatingByBookingId(booking.getBookingId()),
                            Math.toIntExact(booking.getProvider().getUserId()),
                            Math.toIntExact(booking.getSeeker().getUserId())
                    ))
                    .toList();
            return ResponseEntity.ok(Map.of(
                    "data", bookingDTOs1
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving bookings: " + e.getMessage());
        }
    }

    // Method to get all bookings for a specific service provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<?> getBookingsByProvider(@PathVariable Long providerId) {
        try {
            Optional<ServiceProvider> providerOpt = serviceProviderRepo.findById(providerId);
            if (providerOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service provider not found.");
            }

            List<Booking> bookings = bookingRepo.findByProvider(providerOpt.get());
            if (bookings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No bookings found for this provider.");
            }

            // Map Booking to BookingDTO
            List<BookingDTO> bookingDTOs = bookings.stream()
                    .map(booking -> new BookingDTO(
                            booking.getBookingId(),
                            booking.getCategory() != null ? booking.getCategory().getCategoryName() : null,
                            booking.getStatus(),
                            booking.getBookingDate( )
                    ))
                    .toList();

            List<BookingDTO1> bookingDTOs1 = bookings.stream()
                    .map(booking -> new BookingDTO1(
                            booking.getBookingId(),
                            booking.getCategory() != null ? booking.getCategory().getCategoryName() : null,
                            booking.getStatus(),
                            booking.getBookingDate(),
                            ratingService.getRatingByBookingId(booking.getBookingId()),
                            Math.toIntExact(booking.getProvider().getUserId()),
                            Math.toIntExact(booking.getSeeker().getUserId())
                    ))
                    .toList();
            return ResponseEntity.ok(Map.of(
                    "data", bookingDTOs1
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving bookings: " + e.getMessage());
        }
    }


}
