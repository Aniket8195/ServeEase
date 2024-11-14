package com.example.ServeEase.Controller;


import com.example.ServeEase.DTO.UserDTO;
import com.example.ServeEase.Model.Booking;
import com.example.ServeEase.Model.ServiceProvider;
import com.example.ServeEase.Model.ServiceSeeker;
import com.example.ServeEase.Repository.BookingRepo;
import com.example.ServeEase.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userRepo.findAll().stream()
                .map(user -> new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getRole().name(),user.getRating(),
                        0,
                        0,
                        0

                        ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") Long id) {
        // Fetch the user by ID
        Optional<UserDTO> userOpt = userRepo.findById(id).map(user1 -> new UserDTO(
                user1.getUserId(),
                user1.getName(),
                user1.getEmail(),
                user1.getRole().name(),
                user1.getRating(),
                0,  // Placeholder for total bookings
                0,  // Placeholder for completed bookings
                0   // Placeholder for active bookings
        ));

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        UserDTO userDTO = userOpt.get();

        // Fetch bookings associated with the user
        if (userDTO.getRole().equalsIgnoreCase("SEEKER")) {
            ServiceSeeker seeker = new ServiceSeeker();
            seeker.setUserId(id);
            // Fetch total bookings, completed bookings, and active bookings
            userDTO.setTotalBookings(bookingRepo.countBySeeker(seeker));
            userDTO.setCompletedBookings(bookingRepo.countBySeekerAndStatus(seeker, Booking.Status.COMPLETED));
            userDTO.setActiveBookings(bookingRepo.countBySeekerAndStatus(seeker, Booking.Status.CONFIRMED)); // Or other statuses as needed
        } else if (userDTO.getRole().equalsIgnoreCase("PROVIDER")) {
            ServiceProvider provider = new ServiceProvider();
            provider.setUserId(id);
            // Fetch total bookings, completed bookings, and active bookings
            userDTO.setTotalBookings(bookingRepo.countByProvider(provider));
            userDTO.setCompletedBookings(bookingRepo.countByProviderAndStatus(provider, Booking.Status.COMPLETED));
            userDTO.setActiveBookings(bookingRepo.countByProviderAndStatus(provider, Booking.Status.CONFIRMED)); // Or other statuses as needed
        }

        return ResponseEntity.ok(Map.of("user", userDTO));
    }
}
