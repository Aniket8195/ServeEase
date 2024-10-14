package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.BookingDTO;
import com.example.ServeEase.DTO.BookingRequestDTO;
import com.example.ServeEase.Model.Booking;
import com.example.ServeEase.Model.Category;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Repository.BookingRepo;
import com.example.ServeEase.Repository.CategoryRepo;
import com.example.ServeEase.Repository.ServiceProviderRepo;
import com.example.ServeEase.Repository.ServiceSeekerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Booking savedBooking = bookingRepo.save(booking);
            return ResponseEntity.ok("Booking created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while creating booking: " + e.getMessage());
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        return updateBookingStatus(id, Booking.Status.CANCELED);
    }

    // Method to confirm a booking
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id) {
        return updateBookingStatus(id, Booking.Status.CONFIRMED);
    }

    // Method to complete a booking
    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeBooking(@PathVariable Long id) {
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
                    .collect(Collectors.toList());

            return ResponseEntity.ok(bookingDTOs);
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
                    .collect(Collectors.toList());

            return ResponseEntity.ok(bookingDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving bookings: " + e.getMessage());
        }
    }


}
